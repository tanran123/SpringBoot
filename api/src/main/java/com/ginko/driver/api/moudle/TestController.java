package com.ginko.driver.api.moudle;


import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.dao.MongoDBBoxDaoImp;
import com.ginko.driver.framework.entity.BoxEntity;
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

    @Autowired
    private MongoDBBoxDaoImp mongoDBBoxDaoImp;

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public MsgConfig getUserInfo(){
       return new MsgConfig("200","返回成功",200);
    }


    @RequestMapping(value = "/getMongo",method = RequestMethod.POST)
    public MsgConfig getMongo(){
        BoxEntity book = new BoxEntity();
        List<BoxEntity> boxEntities = mongoDBBoxDaoImp.getPage(book,0,500000);
        return new MsgConfig("200",String.valueOf(boxEntities.size()),boxEntities);
    }
}
