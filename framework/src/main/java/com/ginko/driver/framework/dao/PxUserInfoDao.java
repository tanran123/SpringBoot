package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.PxUserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 13:59 2019/7/22
 */
@Repository
public interface PxUserInfoDao extends CrudRepository<PxUserInfo,Long> {
}
