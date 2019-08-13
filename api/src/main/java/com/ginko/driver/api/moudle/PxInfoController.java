package com.ginko.driver.api.moudle;


import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import com.ginko.driver.framework.dao.MongoPxDaoImp;
import com.ginko.driver.framework.dao.MongoSysUserDaoImp;
import com.ginko.driver.framework.entity.*;
import com.ginko.driver.framework.service.LockBuyPxService;
import com.ginko.driver.framework.service.OrderInfoService;
import com.ginko.driver.framework.service.PxUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        if (pxEntities.size() > 0) {
          /*  if (pxEntityP.getUserId() != pxEntities.get(0).getUserId()) {
                return new MsgConfig("401", "这块地属于别的大佬", null);
            }*/
            //存在则修改像素信息
            pxEntity.setR(pxEntityP.getR());
            pxEntity.setG(pxEntityP.getG());
            pxEntity.setB(pxEntityP.getB());
            pxEntity.setA(pxEntityP.getA());
            mongoPxDaoImp.updateFirst(pxEntities.get(0), pxEntity);
        }
        //不存在则新增像素信息
        else {
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
            pxUserInfo.setUserId(0);
            pxUserInfo.setIsSellStatus(1);
            pxUserInfo.setAmount(BigDecimal.valueOf(1));
            pxUserInfo.setXAndY(pxUserInfoP.getX(), pxUserInfoP.getY());
            return new MsgConfig("200", null, pxUserInfo);
        }
        return new MsgConfig("200", null, pxUserInfo);
    }


    /**
     * 修改金额及广告或者用户
     *
     * @param pxUserInfoP
     * @return
     */
    @RequestMapping(value = "/updateAdvertOrAmount", method = RequestMethod.POST)
    public MsgConfig updateAdvertOrAmount(@RequestBody PxUserInfo pxUserInfoP, @RequestHeader("token") String token) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(pxUserInfoP.getUserId());
        sysUser = mongoDBDaoImp.queryOne(sysUser);
        if (!sysUser.getToken().equals(token)) {
            return new MsgConfig("401", MsgEnum.NOROLEAUTH.getDesc(), null);
        }
        PxUserInfo pxUserInfo = pxUserInfoService.findByXAndY(pxUserInfoP);
        //不存在则新增记录
        if (pxUserInfo == null) {
            pxUserInfoService.insertPxUserInfo(pxUserInfoP);
            return new MsgConfig("200", "购买成功", null);
        }
        //存在则修改
        else {
            //排他
            if (!pxUserInfoP.getUpdateTime().equals(pxUserInfo.getUpdateTime())) {
                return new MsgConfig("402", "点已被修改", null);
            }
            pxUserInfo.setAmount(pxUserInfoP.getAmount());
            pxUserInfo.setAdvert(pxUserInfo.getAdvert());
            pxUserInfo.setUserId(pxUserInfoP.getUserId());
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
    @RequestMapping(value = "/getBuyLock", method = RequestMethod.POST)
    public MsgConfig getBuyLock(@RequestBody LockBuyPx lockBuyPxP) {
        LockBuyPx lockBuyPx = lockBuyPxService.findOne(lockBuyPxP);
        //用户当前购买中的点超过最大
        int lockCount = lockBuyPxService.CountUserId(lockBuyPxP);
        if (lockCount>10){
            return new MsgConfig("403", "您已有10个未付款订单，请先付款", false);
        }
        if (lockBuyPx == null) {
            lockBuyPxP.setLockTime(DateTool.getNowTime());
            lockBuyPxService.insert(lockBuyPxP);
            return new MsgConfig("200", null, false);
        } else {
            //如果已被锁定
            if (lockBuyPx.getLockStatus() == 1) {
                //判断是否已过时间
                if (DateTool.diffTimeMin(lockBuyPx.getLockTime()) > 15) {
                    lockBuyPxP.setLockStatus(1);
                    lockBuyPxService.updateLock(lockBuyPxP);
                    return new MsgConfig("200", null, true);
                } else {
                    return new MsgConfig("402", "其他用户购买中", false);
                }
            }
            //如果没被锁定
            else {
                lockBuyPxP.setLockStatus(1);
                lockBuyPxService.updateLock(lockBuyPxP);
                return new MsgConfig("200", null, true);
            }
        }
    }


    /**
     * 回调新增订单
     * @param lockBuyPxP
     * @return
     */
    @RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
    public MsgConfig insertOrder(@RequestBody OrderInfo orderInfo){
       return orderInfoService.insertOrderInfo(orderInfo);
    }
}
