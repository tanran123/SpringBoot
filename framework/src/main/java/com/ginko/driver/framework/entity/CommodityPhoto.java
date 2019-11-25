package com.ginko.driver.framework.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Auther: tran
 * @Date: 2019/11/21
 * @Description:
 */
@Entity
@Table(name = "commodity_photo")
public class CommodityPhoto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private String photoUrl;

    private Double size;

    @Column(name = "commodity_number")
    private String commodityNumber;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }
}
