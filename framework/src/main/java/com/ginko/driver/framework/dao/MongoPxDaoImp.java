package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.PxEntity;
import org.springframework.stereotype.Repository;


@Repository
public class MongoPxDaoImp extends MongoDbDao<PxEntity> {

    @Override
    protected Class<PxEntity> getEntityClass() {
        return PxEntity.class;
    }
}
