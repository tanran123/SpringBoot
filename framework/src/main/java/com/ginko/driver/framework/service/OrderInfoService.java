package com.ginko.driver.framework.service;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.framework.dao.OrderInfoDao;
import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.PxUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 21:36 2019/8/11
 */

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;

    @Autowired
    private PxUserInfoService pxUserInfoService;

    @Transactional
    public MsgConfig insertOrderInfo(OrderInfo orderInfoP){
        PxUserInfo pxUserInfo = new PxUserInfo();
        pxUserInfo.setXAndY(orderInfoP.getX(),orderInfoP.getY());
        PxUserInfo pxUserInfo1 = pxUserInfoService.findByXAndY(pxUserInfo);
        //不存在这个点说明是第一次卖，直接转账给公司，然后新增该点信息
        if (pxUserInfo1 ==null){
            //修改权限
            pxUserInfo.setIsSellStatus(0);
            pxUserInfo.setAdvert("");
            pxUserInfo.setUserId(orderInfoP.getUserId());
            pxUserInfo.setAmount(orderInfoP.getAmount());
            pxUserInfoService.insertPxUserInfo(pxUserInfo);

            //插入购买记录
            orderInfoP.setCreateTime(DateTool.getNowTime());
            orderInfoP.setTransactionStatus(1);
            orderInfoP.setMoneyType(0);
            orderInfoP.setCurrencyType("USD");
            orderInfoP.setDes("买入");
            orderInfoDao.save(orderInfoP);

            //插入出售记录
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserId(8318);
            orderInfo.setDes("卖出");
            orderInfo.setXAndY(orderInfoP.getX(),orderInfoP.getY());
            orderInfo.setMoneyType(1);
            orderInfo.setCurrencyType("USD");
            orderInfo.setCreateTime(DateTool.getNowTime());
            orderInfo.setTransactionStatus(1);
            orderInfo.setAmount(orderInfoP.getAmount());
            orderInfoDao.save(orderInfo);
            return new MsgConfig("200","购买成功",null);
        }
        else{
            //修改权限
            int sellerId = pxUserInfo1.getUserId();
            pxUserInfo1.setIsSellStatus(0);
            pxUserInfo.setAdvert("");
            pxUserInfo1.setUserId(orderInfoP.getUserId());
            pxUserInfo1.setAmount(orderInfoP.getAmount());
            pxUserInfoService.updatePxUserInfo(pxUserInfo1);

            orderInfoP.setCreateTime(DateTool.getNowTime());
            orderInfoP.setUserId(orderInfoP.getUserId());
            orderInfoP.setTransactionStatus(1);
            orderInfoP.setCurrencyType("USD");
            orderInfoDao.save(orderInfoP);

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserId(sellerId);
            orderInfo.setDes("卖出");
            orderInfo.setXAndY(orderInfoP.getX(),orderInfoP.getY());
            orderInfo.setMoneyType(1);
            orderInfo.setCurrencyType("USD");
            orderInfo.setCreateTime(DateTool.getNowTime());
            orderInfo.setTransactionStatus(1);
            orderInfo.setAmount(orderInfoP.getAmount());
            orderInfoDao.save(orderInfo);
            return new MsgConfig("200","购买成功",null);
        }
    }

    public List<OrderInfo> findByUserId(OrderInfo orderInfo){
        return orderInfoDao.findByUserId(orderInfo.getUserId());
    }


    public int update(OrderInfo orderInfo){
        return orderInfoDao.updateOrderTransactionStatus(orderInfo.getId(),orderInfo.getTransactionStatus());
    }
}