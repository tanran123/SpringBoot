package com.ginko.driver.api;

import com.ginko.driver.framework.dao.MongoDBBoxDaoImp;
import com.ginko.driver.framework.dao.MongoDBDaoImp;
import com.ginko.driver.framework.dao.MongoPxDaoImp;
import com.ginko.driver.framework.entity.BoxEntity;
import com.ginko.driver.framework.entity.PxEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private MongoDBBoxDaoImp mongoDBDaoImp;

    @Autowired
    private MongoPxDaoImp mongoPxDaoImp;

    @Test
    public void saveObj() {

        for (int i = 2000000; i < 2003000; i++) {
            BoxEntity book = new BoxEntity();
            book.setUserName(String.valueOf(i));
            book.setMsg("我很帅");
            mongoDBDaoImp.save(book);
        }
    }


    @Test
    public void savePX(){
        for (int i=200;i<=800;i++){
            for (int y=200;y<=800;y++){
                PxEntity pxEntity = new PxEntity();
                pxEntity.setUserId(0);
                pxEntity.setX(i);
                pxEntity.setY(y);
                pxEntity.setR(255);
                pxEntity.setG(255);
                pxEntity.setB(255);
                pxEntity.setA(255);
                mongoPxDaoImp.save(pxEntity);
            }
        }
    }

    @Test
    public void qyObj() {
        BoxEntity book = new BoxEntity();
        List<BoxEntity> boxEntities = mongoDBDaoImp.getPage(book,0,500000);
        System.out.println(boxEntities.size());
    }


    @Test
    public void qyObj1() {
        BoxEntity book = new BoxEntity();
        book.setUserName("1556");
        long test = mongoDBDaoImp.getCount(book);
        System.out.println(test);
    }

    @Test
    public void queryTest() {
        BoxEntity boxEntity = new BoxEntity();
        boxEntity.setMsg("我很帅6");
        List<BoxEntity> boxEntities = mongoDBDaoImp.queryList(boxEntity);
        BoxEntity books = boxEntities.get(0);
        BoxEntity updateBooks = new BoxEntity();
        updateBooks.setUserName("NOTME");
        updateBooks.setId(books.getId());
        updateBooks.setMsg(books.getMsg());
        updateBooks.setColor(books.getColor());
        mongoDBDaoImp.updateFirst(boxEntities.get(0), updateBooks);
        System.out.println(boxEntities.size());
    }



    @Test
    public void save(){

    }


    public void saveObjtest(int x,int y) {

        for (int i = x; i < y; i++) {
            BoxEntity book = new BoxEntity();
            book.setUserName(String.valueOf(i));
            book.setMsg("我很帅" + i);
            mongoDBDaoImp.save(book);
        }
    }
}
