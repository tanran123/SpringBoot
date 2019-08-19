package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.LockBuyPx;
import com.ginko.driver.framework.entity.OrderInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 13:59 2019/7/22
 */
@Repository
public interface LockBuyPxDao extends CrudRepository<LockBuyPx,Long> {

    LockBuyPx save(LockBuyPx lockBuyPx);

    LockBuyPx findByXAndY(int x,int y);

    List<LockBuyPx> findByUserIdAndLockStatus(int userId,int lockStatus);

    int countByUserIdAndLockStatus(int userId,int lockStatus);


    @Transactional
    @Modifying
    @Query(value = "update lock_buy_px  set user_id = ?3,lock_status=?4 ,lock_time = DATE_FORMAT(NOW(),'%Y-%m-%d %T')where x=?1 AND y=?2",nativeQuery = true)
    int updateLockTimeOrUserId(int x,int y,int userId,int status);
}
