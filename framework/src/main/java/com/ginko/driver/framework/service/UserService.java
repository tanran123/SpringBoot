package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.UserIncomDao;
import com.ginko.driver.framework.entity.UserIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserIncomDao incomDao;

    public List<Map<String,Object>> getUserIncomeTop() {
        return incomDao.findByRegisterTop();
    }
}
