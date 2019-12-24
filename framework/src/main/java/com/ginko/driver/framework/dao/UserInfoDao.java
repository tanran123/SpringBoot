package com.ginko.driver.framework.dao;

import com.ginko.driver.framework.entity.UserInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface UserInfoDao extends CrudRepository<UserInfo, Long> {


    @Transactional
    @Modifying
    @Query(value = "update `user` SET wx_head_img_url = ?3,wx_open_id = ?2,nick_name=?4 where user_id = ?1",nativeQuery = true)
    int updateUserOpenIdAndHeadURLAndNickName(int userId, String openId, String headUrl,String nickName);


    UserInfo findByWxOpenId(String openId);

    UserInfo findByDotWalletOpenId(String openId);
}
