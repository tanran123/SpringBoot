package com.ginko.driver.freamwork.dao;

import com.ginko.driver.freamwork.entity.BoxEntity;
import org.springframework.stereotype.Repository;


@Repository
public class MongoDBDaoImp extends MongoDbDao<BoxEntity> {

    @Override
    protected Class<BoxEntity> getEntityClass() {
        return BoxEntity.class;
    }
}
