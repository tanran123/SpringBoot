package com.ginko.driver.framework.entity;

import javax.persistence.*;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 11:21 2019/8/12
 */
@Entity
@Table(name = "lock_buy_px")
public class LockBuyPx {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;

    private int x;

    private int y;

    private String lockTime;

    private int lockStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public void setXAndY(int cx,int cy){
        this.x = cx;
        this.y =cy;
    }

    public int getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
    }
}
