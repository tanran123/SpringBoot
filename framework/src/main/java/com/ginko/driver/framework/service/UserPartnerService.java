package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.UserPartnerDao;
import com.ginko.driver.framework.entity.UserPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserPartnerService {

    @Autowired
    private UserPartnerDao userPartnerDao;

    public UserPartner addUserPartner(UserPartner userPartner){
        return userPartnerDao.save(userPartner);
    }

    public List<UserPartner> findByuserId(int userid){
        return userPartnerDao.findByUserId(userid);
    }

    public List<UserPartner> findByUserPartnerDay(String  day){
        return userPartnerDao.findByPartnerDay(day);
    }


    public Page<UserPartner> findUserPartnerByUserIdAndPage(UserPartner userPartner){
        return userPartnerDao.findByUserId(userPartner.getUserId(), PageRequest.of(userPartner.getPage()-1,userPartner.getSize()));
    }

    public  int updatePartnerIncome(int id, BigDecimal price){
        return userPartnerDao.updatePartnerIncome(id,price);
    }


    public int updatePartnerStatus(int id,int status){
        return userPartnerDao.updatePartnerStatus(id,status);
    }

    public int updateSellStatus(int id,int status){
        return userPartnerDao.updateSellStatus(id,status);
    }
}
