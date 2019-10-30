package com.ginko.driver.framework.service;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.framework.dao.OrderInfoDao;
import com.ginko.driver.framework.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<OrderInfo> findByUserId(OrderInfo orderInfo){
        return orderInfoDao.findByUserIdAndMoneyTypeOrderByCreateTimeDesc(orderInfo.getUserId(),orderInfo.getMoneyType());
    }


    public Page<OrderInfo> findByUserIdAndPage(OrderInfo orderInfo){
        return orderInfoDao.findByUserIdAndMoneyTypeOrderByCreateTimeDesc(orderInfo.getUserId(),orderInfo.getMoneyType(),PageRequest.of(orderInfo.getPage()-1,orderInfo.getSize()));
    }

    public int update(OrderInfo orderInfo){
        return orderInfoDao.updateOrderTransactionStatus(orderInfo.getId(),orderInfo.getTransactionStatus());
    }


}
