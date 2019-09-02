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

    @Transient
    private int page;

    @Transient
    private int size;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
