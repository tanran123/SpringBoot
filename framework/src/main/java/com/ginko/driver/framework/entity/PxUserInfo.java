package com.ginko.driver.framework.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "px_user_info")
public class PxUserInfo implements Serializable {


    private static final long serialVersionUID = 6315911633973351853L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;

    private int x;

    private int y;

    private int isSellStatus;

    private String advert = "";

    private BigDecimal amount = BigDecimal.valueOf(0);

    private String updateTime = "";

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

    public String getAdvert() {
        return advert;
    }

    public void setAdvert(String advert) {
        this.advert = advert;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public void setXAndY(int cx,int cy){
        this.x = cx;
        this.y =cy;
    }

    public int getIsSellStatus() {
        return isSellStatus;
    }

    public void setIsSellStatus(int isSellStatus) {
        this.isSellStatus = isSellStatus;
    }
}
