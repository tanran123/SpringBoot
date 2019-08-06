package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.BoxEntity;
import com.ginko.driver.framework.entity.SysUser;
import org.springframework.stereotype.Repository;


@Repository
public class MongoSysUserDaoImp extends MongoDbDao<SysUser> {

    @Override
    protected Class<SysUser> getEntityClass() {
        return SysUser.class;
    }
}
