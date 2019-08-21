package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.LockBuyPx;
import com.ginko.driver.framework.entity.LogInfo;
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
 * @Date Create in 15:43 2019/8/21
 */

@Repository
public interface LogInfoDao extends CrudRepository<LogInfo,Long> {

    List<LogInfo> findByUserIdAndReadStatus(int userId, int readStatus);

    @Transactional
    @Modifying
    @Query(value = "update log_info set read_status =?2, update_time = DATE_FORMAT(NOW(),'%Y-%m-%d %T') where id=?1",nativeQuery = true)
    int updateLogInfo(int id,int readStatus);
}
