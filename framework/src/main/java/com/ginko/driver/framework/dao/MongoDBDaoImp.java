package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.BoxEntity;
import org.springframework.stereotype.Repository;


@Repository
public class MongoDBDaoImp extends MongoDbDao<BoxEntity> {

    @Override
    protected Class<BoxEntity> getEntityClass() {
        return BoxEntity.class;
    }
}
