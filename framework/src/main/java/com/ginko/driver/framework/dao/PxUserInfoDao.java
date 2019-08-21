package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.PxUserInfo;
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
public interface PxUserInfoDao extends CrudRepository<PxUserInfo,Long> {

    PxUserInfo save(PxUserInfo pxUserInfo);

    PxUserInfo findByXAndY(int x,int y);

    List<PxUserInfo> findByUserId(long id);

    @Transactional
    @Modifying
    @Query(value = "update px_user_info set user_id=?3, advert=?1, amount=?2,is_sell_status = ?4, update_time = DATE_FORMAT(NOW(),'%Y-%m-%d %T') where x=?5 and y = ?6",nativeQuery = true)
    int updatePxUserInfo(String advert, BigDecimal amount,int userId,int sellStatus,int x,int y);
}
