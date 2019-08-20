package com.ginko.driver.framework.entity;

import javax.persistence.Transient;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:44 2019/8/20
 */
public class CommandEntity {
    @Transient
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
