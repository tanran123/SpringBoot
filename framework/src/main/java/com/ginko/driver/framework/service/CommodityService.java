package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.CommodityDao;
import com.ginko.driver.framework.entity.CommodityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @Auther: tran
 * @Date: 2019/11/21
 * @Description:
 */
@Service
public class CommodityService {

    @Autowired
    private CommodityDao commodityDao;

    public Page<CommodityInfo> findByUserId(CommodityInfo commodityInfo){
        return commodityDao.findByUserId(commodityInfo.getUserId(),PageRequest.of(commodityInfo.getPage() - 1, commodityInfo.getSize()));

    }


    public int updateCommodityPrice(CommodityInfo commodityInfo){
        return commodityDao.updateCommodityPrice(commodityInfo.getCommodityNumber(),commodityInfo.getPrice());
    }


    public int updateSellStatus(CommodityInfo commodityInfo){
        return commodityDao.updateSellStatus(commodityInfo.getCommodityNumber(),commodityInfo.getUserSellStatus());
    }
}
