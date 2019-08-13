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
            pxUserInfo.setIsSellStatus(0);
            pxUserInfo.setAdvert("");
            pxUserInfo.setUserId(orderInfoP.getPurchaserId());
            pxUserInfo.setAmount(orderInfoP.getAmount());
            pxUserInfoService.insertPxUserInfo(pxUserInfo);

            orderInfoP.setCreateTime(DateTool.getNowTime());
            orderInfoP.setSellerId(0);
            orderInfoP.setTransactionStatus(1);
            orderInfoP.setCurrencyType("USD");
            orderInfoDao.save(orderInfoP);
            return new MsgConfig("200","购买成功",null);
        }
        else{
            int sellerId = pxUserInfo1.getUserId();
            pxUserInfo1.setIsSellStatus(0);
            pxUserInfo.setAdvert("");
            pxUserInfo1.setUserId(orderInfoP.getPurchaserId());
            pxUserInfo1.setAmount(orderInfoP.getAmount());
            pxUserInfoService.updatePxUserInfo(pxUserInfo1);

            orderInfoP.setCreateTime(DateTool.getNowTime());
            orderInfoP.setSellerId(sellerId);
            orderInfoP.setTransactionStatus(1);
            orderInfoP.setCurrencyType("USD");
            orderInfoDao.save(orderInfoP);
            return new MsgConfig("200","购买成功",null);
        }
    }

    public List<OrderInfo> findBySeller(OrderInfo orderInfo){
        return orderInfoDao.findBySellerId(orderInfo.getSellerId());
    }

    public List<OrderInfo> findByPurchaser(OrderInfo orderInfo){
        return orderInfoDao.findByPurchaserId(orderInfo.getPurchaserId());
    }

    public int update(OrderInfo orderInfo){
        return orderInfoDao.updateOrderTransactionStatus(orderInfo.getId(),orderInfo.getTransactionStatus());
    }
}
