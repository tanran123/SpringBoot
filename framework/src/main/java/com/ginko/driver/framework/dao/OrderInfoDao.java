package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.UserOrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 13:59 2019/7/22
 */
@Repository
public interface OrderInfoDao extends CrudRepository<UserOrderInfo,Long> {

     List<UserOrderInfo> findAll();

     List<UserOrderInfo> findByUserId(int userId);

     Page<UserOrderInfo> findByUserIdAndOrderStatus(int userId,int orderStatus,Pageable pageable);


     @Transactional
     @Modifying
     @Query(value = "update user_order SET  order_status=?1 where order_number=?2",nativeQuery = true)
     int updateUserOrder(int orderStatus,String orderNumber);
}
