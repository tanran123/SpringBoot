package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.UserIncomDao;
import com.ginko.driver.framework.entity.UserIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: tran
 * @Date: 2019/11/26
 * @Description:
 */
@Service
public class UserIncomeService {
    @Autowired
    private UserIncomDao userIncomDao;

    public UserIncome addIncome(UserIncome userIncome){
        return null;
    }

}
