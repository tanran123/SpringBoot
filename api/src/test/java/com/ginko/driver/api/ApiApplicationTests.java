package com.ginko.driver.api;

import com.ginko.driver.freamwork.dao.MongoDBDaoImp;
import com.ginko.driver.freamwork.entity.BoxEntity;
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
    private MongoDBDaoImp mongoDBDaoImp;

    @Test
    public void saveObj() {

        for (int i = 1000000; i < 2000000; i++) {
            BoxEntity book = new BoxEntity();
            book.setUserName("tran");
            book.setMsg("我很帅" + i);
            mongoDBDaoImp.save(book);
        }
    }

    @Test
    public void qyObj() {
        BoxEntity book = new BoxEntity();
        book.setMsg("我很帅5");
        List<BoxEntity> boxEntities = mongoDBDaoImp.queryList(book);
 /*       book = boxEntities.get(0);
        book.setUserName("sww");
        mongoDBDaoImp.delete(boxEntities.get(0));
        mongoDBDaoImp.save(book);*/
        System.out.println(boxEntities.size());
    }


    @Test
    public void qyObj1() {
        BoxEntity book = new BoxEntity();
        book.setUserName("tran");
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
}
