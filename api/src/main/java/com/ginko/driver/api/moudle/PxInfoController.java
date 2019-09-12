package com.ginko.driver.api.moudle;


import com.ginko.driver.api.webSocket.CustomerWebSoket;
import com.ginko.driver.api.webSocket.PxBuySocket;
import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import com.ginko.driver.framework.dao.MongoPxDaoImp;
import com.ginko.driver.framework.dao.MongoSysUserDaoImp;
import com.ginko.driver.framework.entity.*;
import com.ginko.driver.framework.service.LockBuyPxService;
import com.ginko.driver.framework.service.LogInfoService;
import com.ginko.driver.framework.service.OrderInfoService;
import com.ginko.driver.framework.service.PxUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/px")
public class PxInfoController {
    Logger logger = LoggerFactory.getLogger(PxInfoController.class);

    @Autowired
    private MongoPxDaoImp mongoPxDaoImp;

    @Autowired
    private PxUserInfoService pxUserInfoService;

    @Autowired
    private MongoSysUserDaoImp mongoDBDaoImp;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private LockBuyPxService lockBuyPxService;

    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public MsgConfig getUserInfo() {
        return new MsgConfig("200", "返回成功", 200);
    }


    /**
     * 从MONGODB中请求数据
     *
     * @return
     */
    @RequestMapping(value = "/getMongo", method = RequestMethod.POST)
    public MsgConfig getMongo() {
        logger.info("-----------------请求像素数据-----------------");
        PxEntity pxEntity = new PxEntity();
        List<PxEntity> boxEntities = mongoPxDaoImp.queryList(pxEntity);
        return new MsgConfig("200", String.valueOf(boxEntities.size()), boxEntities);
    }

    /**
     * 修改像素点
     *
     * @param pxEntityP
     * @return
     */
    @RequestMapping(value = "/updatePx", method = RequestMethod.POST)
    public MsgConfig updatePx(@RequestBody PxEntity pxEntityP) {
        logger.info("-----------------修改像素数据-----------------x:" + pxEntityP.getX() + "y:" + pxEntityP.getY());
        PxEntity pxEntity = new PxEntity();
        pxEntity.setX(pxEntityP.getX());
        pxEntity.setY(pxEntityP.getY());
        List<PxEntity> pxEntities = mongoPxDaoImp.queryList(pxEntity);
        PxUserInfo pxUserInfo = new PxUserInfo();
        pxUserInfo.setXAndY(pxEntityP.getX(),pxEntityP.getY());
        pxUserInfo = pxUserInfoService.findByXAndY(pxUserInfo);

        if (pxEntities.size() > 0) {
//            看是否在私有区内
            if (pxEntityP.getX() >= 300 && pxEntityP.getX() < 900 && pxEntityP.getY() >= 200 && pxEntityP.getY() <= 600) {
//                点不属于他
                if (pxEntityP.getUserId() != pxUserInfo.getUserId()) {
                    return new MsgConfig("402", "不能在其他私有场地涂画哦", null);
                }
            }
            //存在则修改像素信息
            pxEntity.setR(pxEntityP.getR());
            pxEntity.setG(pxEntityP.getG());
            pxEntity.setB(pxEntityP.getB());
            pxEntity.setA(pxEntityP.getA());
            mongoPxDaoImp.updateFirst(pxEntities.get(0), pxEntity);
        }
        //不存在则新增像素信息
        else {
            if (pxEntityP.getX() >= 300 && pxEntityP.getX() < 900 &&
                    pxEntityP.getY() >= 200 && pxEntityP.getY() <= 600) {
                int userId = pxUserInfo==null?0:pxUserInfo.getUserId();
                if (pxEntityP.getUserId() != userId) {
                    return new MsgConfig("402", "不能在其他私有场地涂画哦", null);
                }
            }
            pxEntity.setUserId(pxEntityP.getUserId());
            pxEntity.setR(pxEntityP.getR());
            pxEntity.setG(pxEntityP.getG());
            pxEntity.setB(pxEntityP.getB());
            pxEntity.setA(pxEntityP.getA());
            mongoPxDaoImp.save(pxEntity);
        }
        System.out.println(pxEntities.size());
        return new MsgConfig("200", "修改成功", pxEntity);
    }


    /**
     * 获取改点广告等信息
     *
     * @param pxUserInfoP
     * @return
     */
    @RequestMapping(value = "/getPxUser", method = RequestMethod.POST)
    public MsgConfig getPxUser(@RequestBody PxUserInfo pxUserInfoP) {
        PxUserInfo pxUserInfo = pxUserInfoService.findByXAndY(pxUserInfoP);
        //不存在记录则说明这个点没卖
        if (pxUserInfo == null) {
            pxUserInfo = new PxUserInfo();
            pxUserInfo.setUserId(9999999);
            pxUserInfo.setIsSellStatus(1);
            pxUserInfo.setAmount(BigDecimal.valueOf(1));
            pxUserInfo.setXAndY(pxUserInfoP.getX(), pxUserInfoP.getY());
            return new MsgConfig("200", null, pxUserInfo);
        }
        return new MsgConfig("200", null, pxUserInfo);
    }


    /**
     * 修改金额及广告
     *
     * @param pxUserInfoP
     * @return
     */
    @RequestMapping(value = "/updateAdvertOrAmount", method = RequestMethod.POST)
    public MsgConfig updateAdvertOrAmount(@RequestBody PxUserInfo pxUserInfoP) {
        PxUserInfo pxUserInfo = pxUserInfoService.findByXAndY(pxUserInfoP);
        //不存在则新增记录
        if (pxUserInfo == null) {
            pxUserInfoService.insertPxUserInfo(pxUserInfoP);
            return new MsgConfig("200", "修改成功", null);
        }
        //存在则修改
        else {
            //排他
            if (!pxUserInfoP.getUpdateTime().equals(pxUserInfo.getUpdateTime())) {
                return new MsgConfig("402", "点已被修改", null);
            }
            pxUserInfo.setAmount(pxUserInfoP.getAmount());
            pxUserInfo.setAdvert(pxUserInfoP.getAdvert());
            pxUserInfo.setIsSellStatus(pxUserInfoP.getIsSellStatus());
            pxUserInfoService.updatePxUserInfo(pxUserInfo);
            return new MsgConfig("200", "修改成功", pxUserInfo);
        }
    }

    /**
     * 获取用户的所有能修改的点
     *
     * @param pxUserInfoP
     * @return
     */
    @RequestMapping(value = "/getUserPxList", method = RequestMethod.POST)
    public MsgConfig getUserPxList(@RequestBody PxUserInfo pxUserInfoP) {
        List<PxUserInfo> pxUserInfoList = pxUserInfoService.findByUserId(pxUserInfoP);
        if (pxUserInfoList.size() <= 0) {
            return new MsgConfig("500", "您无私有的点", null);
        }
        return new MsgConfig("200", null, pxUserInfoList);
    }


    /**
     * 获取该点是否已被交易
     *
     * @param lockBuyPxP
     * @return
     */
    @RequestMapping(value = "/updateBuyLock", method = RequestMethod.POST)
    public MsgConfig updateBuyLock(@RequestBody LockBuyPx lockBuyPxP) {
        LockBuyPx lockBuyPx = lockBuyPxService.findOne(lockBuyPxP);
        //用户当前购买中的点超过最大
        int lockCount = lockBuyPxService.CountUserId(lockBuyPxP);
        if (lockCount > 10 && lockBuyPxP.getLockStatus() == 1) {
            return new MsgConfig("403", "您已有10个未付款订单，请先完成付款", false);
        }
        if (lockBuyPx == null) {
            lockBuyPxP.setLockTime(DateTool.getNowTime());
            lockBuyPxP.setLockStatus(1);
            lockBuyPxService.insert(lockBuyPxP);
            return new MsgConfig("200", null, false);
        }
        //如果存在则锁定
        else {
            lockBuyPxService.updateLock(lockBuyPxP);
            return new MsgConfig("200", null, true);
        }
    }


    /**
     * 查询用户锁定的点
     *
     * @param lockBuyPxP
     * @return
     */
    @RequestMapping(value = "/getBuyLockXAndY", method = RequestMethod.POST)
    public MsgConfig getBuyLockXAndY(@RequestBody LockBuyPx lockBuyPxP) {
        LockBuyPx lockBuyPx = lockBuyPxService.findOne(lockBuyPxP);
        if (lockBuyPx != null) {
            //如果被锁定
            if (lockBuyPx.getLockStatus() == 1) {
                // 如果锁定时间已过
                if (DateTool.diffTimeMin(lockBuyPx.getLockTime()) > 15) {
                    return new MsgConfig("200", null, null);
                }


                //判断是否为自己锁定
                if (lockBuyPx.getUserId() == lockBuyPxP.getUserId()) {
                    return new MsgConfig("201", null, lockBuyPx);
                }
//                不是自己锁定
                else {
                    return new MsgConfig("401", null, lockBuyPx);
                }
            }
        }
        return new MsgConfig("200", null, null);
    }


    /**
     * 回调新增订单
     *
     * @param lockBuyPxP
     * @return
     */
    @RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
    public MsgConfig insertOrder(@RequestBody OrderInfo orderInfo) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(orderInfo.getUserId());
        sysUser = mongoDBDaoImp.queryOne(sysUser);
        if (!sysUser.getToken().equals(orderInfo.getToken())) {
            return new MsgConfig("401", MsgEnum.NOROLEAUTH.getDesc(), null);
        }
        //用户购买后解除锁定
        LockBuyPx lockBuyPx = new LockBuyPx();
        lockBuyPx.setXAndY(orderInfo.getX(), orderInfo.getY());
        lockBuyPx.setLockStatus(0);
        lockBuyPx.setUserId(orderInfo.getUserId());
        lockBuyPxService.updateLock(lockBuyPx);
        MsgConfig msgConfig = orderInfoService.insertOrderInfo(orderInfo);
        LogInfo logInfo = new LogInfo();
        logInfo.setCreateTime(DateTool.getNowTime());
        logInfo.setMsg("您坐标为(" + orderInfo.getX() + "," + orderInfo.getY() + ")的像素点以$"+orderInfo.getAmount()+"出售");
        logInfo.setReadStatus(1);
        logInfo.setUserId(orderInfo.getSellerId());
        logInfo.setLogType(2);
        logInfoService.insertLogInfo(logInfo);
//        给SELLER发送信息
        try {
            PxBuySocket.sendByUserId(orderInfo.getSellerId(), logInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogInfo logInfo1 = new LogInfo();
        logInfo1.setMsg("您以$"+orderInfo.getAmount()+"购买坐标为(" + orderInfo.getX() + "," + orderInfo.getY() + ")的像素点");
        logInfo1.setUserId(orderInfo.getUserId());
        logInfo1.setLogType(1);
        logInfo1.setCreateTime(DateTool.getNowTime());
        logInfo1.setReadStatus(1);
        logInfoService.insertLogInfo(logInfo1);
//发送信息嗷
        try {
            PxBuySocket.sendByUserId(orderInfo.getUserId(), logInfo1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msgConfig;
    }


    @RequestMapping(value = "/sendSocket", method = RequestMethod.POST)
    public void send() {
        try {
            PxBuySocket.sendByUserId(8318, "兄弟我给你发信息了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getBuyLockList", method = RequestMethod.POST)
    public MsgConfig getBuyLockList(@RequestBody LockBuyPx lockBuyPx) {
        List<LockBuyPx> lockBuyPxes = lockBuyPxService.findByUserId(lockBuyPx);
        return new MsgConfig("200", null, lockBuyPxes);
    }


    /**
     * 获取订单表
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/getOrderList",method = RequestMethod.POST)
    public MsgConfig getOrderList(@RequestBody OrderInfo orderInfo){
        List<OrderInfo> orderInfoList = orderInfoService.findByUserId(orderInfo);
        return new MsgConfig("200", null, orderInfoList);
    }


    /**
     * 获取订单表(分页)
     * @param orderInfo
     * @return
     */
    @RequestMapping(value = "/getOrderListPage",method = RequestMethod.POST)
    public MsgConfig getOrderListPage(@RequestBody OrderInfo orderInfo){
        Page<OrderInfo> orderInfoList = orderInfoService.findByUserIdAndPage(orderInfo);
        return new MsgConfig("200", null, orderInfoList);
    }
}
