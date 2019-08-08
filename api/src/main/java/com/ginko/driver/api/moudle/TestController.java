package com.ginko.driver.api.moudle;


import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.dao.MongoDBBoxDaoImp;
import com.ginko.driver.framework.dao.MongoPxDaoImp;
import com.ginko.driver.framework.entity.BoxEntity;
import com.ginko.driver.framework.entity.PxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MongoDBBoxDaoImp mongoDBBoxDaoImp;

    @Autowired
    private MongoPxDaoImp mongoPxDaoImp;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public MsgConfig getUserInfo() {
        return new MsgConfig("200", "返回成功", 200);
    }


    @RequestMapping(value = "/getMongo", method = RequestMethod.POST)
    public MsgConfig getMongo() {
        logger.info("-----------------请求像素数据-----------------");
        PxEntity pxEntity = new PxEntity();
        List<PxEntity> boxEntities = mongoPxDaoImp.queryList(pxEntity);
        return new MsgConfig("200", String.valueOf(boxEntities.size()), boxEntities);
    }

    @RequestMapping(value = "/updatePx", method = RequestMethod.POST)
    public MsgConfig updatePx(@RequestBody PxEntity pxEntityP) {
        logger.info("-----------------修改像素数据-----------------x:" + pxEntityP.getX() + "y:" + pxEntityP.getY());
        PxEntity pxEntity = new PxEntity();
        pxEntity.setX(pxEntityP.getX());
        pxEntity.setY(pxEntityP.getY());
        List<PxEntity> pxEntities = mongoPxDaoImp.queryList(pxEntity);
        if (pxEntities.size() > 0) {
          /*  if (pxEntityP.getUserId() != pxEntities.get(0).getUserId()) {
                return new MsgConfig("401", "这块地属于别的大佬", null);
            }*/
            //存在则修改像素信息
            pxEntity.setR(pxEntityP.getR());
            pxEntity.setG(pxEntityP.getG());
            pxEntity.setB(pxEntityP.getB());
            pxEntity.setA(pxEntityP.getA());
            mongoPxDaoImp.updateFirst(pxEntities.get(0), pxEntity);
        }
        //不存在则新增像素信息
        else {
            pxEntity.setUserId(pxEntityP.getUserId());
            pxEntity.setR(pxEntityP.getR());
            pxEntity.setG(pxEntityP.getG());
            pxEntity.setB(pxEntityP.getB());
            pxEntity.setA(pxEntityP.getA());
            mongoPxDaoImp.save(pxEntity);
        }
        System.out.println(pxEntities.size());
        return new MsgConfig("200", "修改成功", pxEntity);
    }
}
