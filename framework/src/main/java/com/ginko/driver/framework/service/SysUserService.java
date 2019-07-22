package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.SysUserDao;
import com.ginko.driver.framework.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:04 2019/7/22
 */
@Service
public class SysUserService {
    @Autowired
    private SysUserDao sysUserDao;


    public SysUser findByuserName(String userName){
        return sysUserDao.findByUserName(userName);
    }

    public SysUser findByuserNameAndPassword(String userName,String password){
        return sysUserDao.findByUserNameAndPassword(userName,password);
    }

    public SysUser saveUser(SysUser user){
        return sysUserDao.save(user);
    }
}
