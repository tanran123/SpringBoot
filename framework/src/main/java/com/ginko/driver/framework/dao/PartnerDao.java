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

    Partner findByPartnerId(int partnerId);

    List<Partner> findByPartnerUserId(int userId);


    Page<Partner> findByPartnerUserId(int userId,Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update partner  price = ?2, partner_user_id = ?1 where partner_id=?3",nativeQuery = true)
    int updatePartnerPrice(int userId, BigDecimal price,int id);


    @Transactional
    @Modifying
    @Query(value = "update partner SET  sell_status = ?2, partner_user_id = ?1 where partner_id=?3",nativeQuery = true)
    int updatePartnerSellStatus(int userId, int sellStatus,int id);


    @Transactional
    @Modifying
    @Query(value = "update partner SET  lock_status = ?2, lock_time = ?3,lock_user_id =?4 where partner_id=?1",nativeQuery = true)
    int updateLockStatusAndLockTimeAndUserId(int partnerId, int lockStatus,String dataTime,int userId);

}
