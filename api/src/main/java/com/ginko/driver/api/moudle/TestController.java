package com.ginko.driver.api.moudle;


import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.dao.MongoDBBoxDaoImp;
import com.ginko.driver.framework.dao.MongoPxDaoImp;
import com.ginko.driver.framework.entity.BoxEntity;
import com.ginko.driver.framework.entity.PxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MongoDBBoxDaoImp mongoDBBoxDaoImp;

    @Autowired
    private MongoPxDaoImp mongoPxDaoImp;

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public MsgConfig getUserInfo(){
       return new MsgConfig("200","返回成功",200);
    }


    @RequestMapping(value = "/getMongo",method = RequestMethod.POST)
    public MsgConfig getMongo(){
        logger.info("-----------------请求像素数据-----------------");
        PxEntity pxEntity = new PxEntity();
        pxEntity.setUserId(0);
        List<PxEntity> boxEntities = mongoPxDaoImp.getPage(pxEntity,0,360000);
        return new MsgConfig("200",String.valueOf(boxEntities.size()),boxEntities);
    }
}
