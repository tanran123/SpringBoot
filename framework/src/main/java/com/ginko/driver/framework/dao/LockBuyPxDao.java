package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.LockBuyPx;
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
public interface LockBuyPxDao extends CrudRepository<LockBuyPx,Long> {

    LockBuyPx save(LockBuyPx lockBuyPx);

    LockBuyPx findByXAndY(int x,int y);

    @Query(value = "select id,user_id,x,y,lock_time,lock_status,seller_id,amount from lock_buy_px where user_id=?1 and lock_status = ?2 and TIMESTAMPDIFF(MINUTE,lock_time,NOW())<15 order by id desc",nativeQuery = true)
    List<LockBuyPx> findByUserIdAndLockStatus(int userId,int lockStatus);

    @Query(value = "select COUNT(id) from lock_buy_px where user_id=?1 and lock_status = 1 and TIMESTAMPDIFF(MINUTE,lock_time,NOW())<15",nativeQuery = true)
    int countByUserIdAndLockStatus(int userId,int lockStatus);


    @Transactional
    @Modifying
    @Query(value = "update lock_buy_px  set user_id = ?3,lock_status=?4 ,amount=?5,lock_time = DATE_FORMAT(NOW(),'%Y-%m-%d %T')where x=?1 AND y=?2",nativeQuery = true)
    int updateLockTimeOrUserId(int x, int y, int userId, int status, BigDecimal amount);
}
