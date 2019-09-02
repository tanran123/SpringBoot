package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.LogInfoDao;
import com.ginko.driver.framework.entity.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 15:58 2019/8/21
 */
@Service
public class LogInfoService {

    @Autowired
    private LogInfoDao logInfoDao;

    public LogInfo insertLogInfo(LogInfo logInfo){
        return logInfoDao.save(logInfo);
    }

    public List<LogInfo> findByUserIdAndReadStatus(LogInfo logInfo){
       return logInfoDao.findByUserIdAndReadStatusOrderByCreateTimeDesc(logInfo.getUserId(),logInfo.getReadStatus());
    }

    public int updateLogInfo(LogInfo logInfo){
        return logInfoDao.updateLogInfo(logInfo.getId(),logInfo.getReadStatus());
    }



}
