package com.ginko.driver.framework.service;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.util.DateTool;
import com.ginko.driver.framework.dao.OrderInfoDao;
import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.UserOrderInfo;
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

    public Page<UserOrderInfo> findByUserOrder(UserOrderInfo userOrderInfo){
        return orderInfoDao.findByUserIdAndOrderStatus(userOrderInfo.getUserId(),userOrderInfo.getOrderStatus(),PageRequest.of(userOrderInfo.getPage() - 1, userOrderInfo.getSize()));
    }

    public int updateUserOrder(UserOrderInfo userOrderInfo){
        return orderInfoDao.updateUserOrder(userOrderInfo.getOrderStatus(),userOrderInfo.getOrderNumber());
    }

}
