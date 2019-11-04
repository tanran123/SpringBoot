package com.ginko.driver.api;

import com.ginko.driver.api.moudle.partner.PartnerController;
import com.ginko.driver.framework.dao.UserPartnerDao;
import com.ginko.driver.framework.entity.UserPartner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @Auther: tran
 * @Date: 2019/11/4
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PartnerTest {
    @Autowired
    private UserPartnerDao userPartnerDao;

    @Test
    public void testD() {
        List<UserPartner> userPartner = userPartnerDao.findByUserId(1);
        System.out.println(userPartner);
    }

}
