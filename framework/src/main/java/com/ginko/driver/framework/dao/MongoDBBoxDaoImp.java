package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.BoxEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 10:41 2019/7/30
 */
@Repository
public class MongoDBBoxDaoImp extends MongoDbDao<BoxEntity> {
    @Override
    protected Class<BoxEntity> getEntityClass() {
        return BoxEntity.class;
    }
}
