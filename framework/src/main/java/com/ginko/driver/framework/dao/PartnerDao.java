package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.Partner;
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
public interface PartnerDao extends CrudRepository<Partner, Long> {


    Partner save(Partner partner);

    /**
     * 查询日期patner
     * @return
     */
    Partner findByPartnerDay(String partnerDay);

    /**
     * 通过主键查询
     * @param partnerId
     * @return
     */
    Partner findByPartnerId(int partnerId);

    /**
     * 查询用户全拥有的全部partner
     * @param userId
     * @return
     */
    List<Partner> findByPartnerUserId(int userId);


    /**
     * 用过USERID分页查询
     * @param userId
     * @param pageable
     * @return
     */
    Page<Partner> findByPartnerUserId(int userId,Pageable pageable);

    /**
     * 通过isSellStatus分页查询
     * @param sellStatus
     * @return
     */
    Page<Partner> findBySellStatus(int sellStatus,Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update partner SET  price = ?2,bsv_price = ?4  where partner_day=?3 and partner_user_id = ?1",nativeQuery = true)
    int updatePartnerPrice(int userId, BigDecimal price,String partnerDay,BigDecimal bsvPrice);


    @Transactional
    @Modifying
    @Query(value = "update partner SET  sell_status = ?2, partner_user_id = ?1 ,price = ?4 where partner_id=?3",nativeQuery = true)
    int updatePartnerSellStatus(int userId, int sellStatus,int id,BigDecimal price);

    @Transactional
    @Modifying
    @Query(value = "update partner SET  sell_status = ?1, price = ?3 ,bsv_price=?4 where partner_id=?2",nativeQuery = true)
    int updatePartnerSellStatusAndPrice(int sellStatus,int id,BigDecimal price,BigDecimal bsvPrice);


    @Transactional
    @Modifying
    @Query(value = "update partner SET  lock_status = ?2, lock_time = ?3,lock_user_id =?4 where partner_id=?1",nativeQuery = true)
    int updateLockStatusAndLockTimeAndUserId(int partnerId, int lockStatus,String dataTime,int userId);


    @Transactional
    @Modifying
    @Query(value = "update partner SET  view_count=view_count+1 where partner_day=?1",nativeQuery = true)
    int updatePartnerViewCount(String partnerDay);

}
