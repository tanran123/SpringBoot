package com.ginko.driver.framework.service;

import com.ginko.driver.framework.dao.UserIncomDao;
import com.ginko.driver.framework.dao.UserInfoDao;
import com.ginko.driver.framework.entity.UserIncome;
import com.ginko.driver.framework.entity.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserIncomDao incomDao;

    @Autowired
    private UserInfoDao userInfoDao;


    public List<Map<String,Object>> getUserIncomeTop() {
      return incomDao.findByRegisterTop();
    }

    public int updateWxInfo(UserInfo userInfo){
        return userInfoDao.updateUserOpenIdAndHeadURLAndNickName(userInfo.getUserId(),userInfo.getWxOpenId(),
                userInfo.getWxHeadImgUrl(),userInfo.getNickName());
    }


    public UserInfo findByOpenId(UserInfo userInfo){
        return userInfoDao.findByWxOpenId(userInfo.getWxOpenId());
    }
}
