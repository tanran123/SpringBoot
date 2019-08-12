package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.PxUserInfoDao;
import com.ginko.driver.framework.entity.PxUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 10:44 2019/8/10
 */
@Service
public class PxUserInfoService {

    @Autowired
    private PxUserInfoDao pxUserInfoDao;

    public PxUserInfo findByXAndY(PxUserInfo pxUserInfo){
        return pxUserInfoDao.findByXAndY(pxUserInfo.getX(),pxUserInfo.getY());
    }

    public List<PxUserInfo> findByUserId(PxUserInfo pxUserInfo){
        return pxUserInfoDao.findByUserId(pxUserInfo.getUserId());
    }


    public int updatePxUserInfo(PxUserInfo pxUserInfo){
        return pxUserInfoDao.updatePxUserInfo(pxUserInfo.getId(),pxUserInfo.getAdvert(),pxUserInfo.getAmount(),pxUserInfo.getUserId(),pxUserInfo.getIsSellStatus());
    }

    public PxUserInfo insertPxUserInfo(PxUserInfo pxUserInfo){
        return pxUserInfoDao.save(pxUserInfo);
    }
}
