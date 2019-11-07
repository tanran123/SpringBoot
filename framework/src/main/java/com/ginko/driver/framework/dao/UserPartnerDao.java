package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserPartnerDao extends CrudRepository<UserPartner, Long> {
    UserPartner save(UserPartner partner);

    /**
     * 查询用户的所有信息
     * @return
     */
    Page<UserPartner> findByUserId(int userId, Pageable pageable);


    /**
     * 查询用户的所有信息
     * @return
     */
    Page<UserPartner> findByUserIdAndPartnerStatus(int userId,int partnerStatus,Pageable pageable);

    UserPartner findByPartnerIdAndUserIdAndPaymentStatus(int partnerId,int userId,int paymentStatus);

    UserPartner findByOrderId(String orderId);
    /**
     * 查询用户持有的所有
     * @return
     */
    List<UserPartner> findByUserId(int userId);



    /**
     * 查询某天合伙人的持有历史记录
     * @return
     */
    List<UserPartner> findByPartnerDay(String day);

    /**
     * 更新用户持有的
     * @param userId
     * @param partnerId
     * @param income
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update user_partner SET  partner_incom = ?2 where id = ?1",nativeQuery = true)
    int updatePartnerIncome(int id,BigDecimal income);

    /**
     * 更新用户持有的合伙人状态
     * @param userId
     * @param partnerId
     * @param status
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update user_partner SET  partner_status = ?2 where id=?1",nativeQuery = true)
    int updatePartnerStatus(int id,int status);


    @Transactional
    @Modifying
    @Query(value = "update user_partner SET  sell_status = ?2 where id=?1",nativeQuery = true)
    int updateSellStatus(int id,int status);


    @Transactional
    @Modifying
    @Query(value = "update user_partner SET  partner_status = 1,sell_datetime=?3 where partner_id=?1 and user_id=?2 and partner_status = 0",nativeQuery = true)
    int updateUserPartnerOwn(int partnerId,int sellUserId,String dateTime);

    @Transactional
    @Modifying
    @Query(value = "update user_partner SET  partner_status = 0,payment_status=1 where order_id=?1",nativeQuery = true)
    int updateUserPartnerOwnForOderId(String orderId);

    @Transactional
    @Modifying
    @Query(value = "update user_partner SET  payment_status=?2 where order_id=?1",nativeQuery = true)
    int updatePaymentStatusForOrderId(String orderId,int paymentStatus);
}
