package com.ginko.driver.framework.service;


import com.ginko.driver.framework.dao.PartnerDao;
import com.ginko.driver.framework.dao.UserPartnerDao;
import com.ginko.driver.framework.entity.OrderInfo;
import com.ginko.driver.framework.entity.Partner;
import com.ginko.driver.framework.entity.UserPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PartnerService {

    @Autowired
    private PartnerDao partnerDao;


    public Partner addPartner(Partner partner){
        return partnerDao.save(partner);
    }


    /**
     * 查找某一天的合伙人
     * @param day
     * @return
     */
    public Partner findByPartnerDay(String day){
        return partnerDao.findByPartnerDay(day);
    }


    public Page<Partner> findByPartnerUserId(UserPartner userPartner){
        return partnerDao.findByPartnerUserId(userPartner.getUserId(), PageRequest.of(userPartner.getPage()-1,userPartner.getSize()));
    }

    public List<Partner> findByPartnerUserId(int userId){
        return partnerDao.findByPartnerUserId(userId);
    }





}
