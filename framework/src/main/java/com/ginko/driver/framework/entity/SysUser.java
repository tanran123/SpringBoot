package com.ginko.driver.framework.entity;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.*;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 13:55 2019/7/22
 */

@Entity
@Table(name = "sys_user")
public class SysUser extends CommandEntity {
    @Id
    @Indexed
    private int userId;

    @Indexed
    private String userName;

    private String password;

    private String type;

    private String nickName;

    private String icon;

    private String updateUser;

    private String updateTime;

    private String token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
