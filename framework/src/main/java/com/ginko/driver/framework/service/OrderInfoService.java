package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.OrderInfoDao;
import com.ginko.driver.framework.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public OrderInfo insertOrderInfo(OrderInfo orderInfo){
           return orderInfoDao.save(orderInfo);
    }


}
