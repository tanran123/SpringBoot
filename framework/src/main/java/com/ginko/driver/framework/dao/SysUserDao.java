package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 13:59 2019/7/22
 */
@Repository
public interface SysUserDao extends CrudRepository<SysUser,Long> {
     SysUser findByUserName(String userNmae);

     SysUser findByUserId(int userId);

     SysUser findByUserNameAndPassword(String username,String password);

     SysUser save(SysUser sysUser);
}
