package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.LockBuyPxDao;
import com.ginko.driver.framework.entity.LockBuyPx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:18 2019/8/12
 */
@Service
public class LockBuyPxService {

    @Autowired
    private LockBuyPxDao lockBuyPxDao;

    public LockBuyPx insert(LockBuyPx lockBuyPx) {
        return lockBuyPxDao.save(lockBuyPx);
    }

    public LockBuyPx findOne(LockBuyPx lockBuyPx) {
        return lockBuyPxDao.findByXAndY(lockBuyPx.getX(), lockBuyPx.getY());
    }

    public List<LockBuyPx> findByUserId(LockBuyPx lockBuyPx) {
        return lockBuyPxDao.findByUserIdAndLockStatus(lockBuyPx.getUserId(),lockBuyPx.getLockStatus());
    }

    public int updateLock(LockBuyPx lockBuyPx) {
        return lockBuyPxDao.updateLockTimeOrUserId(lockBuyPx.getX(),lockBuyPx.getY(),lockBuyPx.getUserId(),lockBuyPx.getLockStatus());
    }

    public int CountUserId(LockBuyPx lockBuyPx){
         lockBuyPx.setLockStatus(1);
         return lockBuyPxDao.countByUserIdAndLockStatus(lockBuyPx.getUserId(),lockBuyPx.getLockStatus());
    }
}
