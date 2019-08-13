package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.OrderInfo;
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
public interface OrderInfoDao extends CrudRepository<OrderInfo,Long> {

    OrderInfo save(OrderInfo orderInfo);

    /**
     * 卖方ID
     * @param sellerId
     * @return
     */
    List<OrderInfo> findBySellerId(int sellerId);

    /**
     * 买方ID
     * @param purchaserId
     * @return
     */
    List<OrderInfo> findByPurchaserId(int purchaserId);


    @Transactional
    @Modifying
    @Query(value = "update sys_order_info  transaction_status = ?2, update_time = DATE_FORMAT(NOW(),'%Y-%m-%d %T')where user_id=?1",nativeQuery = true)
    int updateOrderTransactionStatus(int id,Integer status);
}
