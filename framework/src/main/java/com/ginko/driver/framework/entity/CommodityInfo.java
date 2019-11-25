package com.ginko.driver.framework.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "commodity")
public class CommodityInfo extends CommandEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commodityId;

    @Column(name = "commodity_number")
    private String commodityNumber;

    private String partnerDay;

    private String title;

    private BigDecimal price;

    private String description;


    @Transient
    private String label;

    private int userId;

    private int userSellStatus;

    private String time;


    @OneToMany(fetch = FetchType.LAZY, targetEntity = CommodityFile.class)
    @JoinColumn(name="commodity_number",insertable = false,updatable = false,referencedColumnName = "commodity_number")
    private Set<CommodityFile> commodityFiles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = CommodityPhoto.class)
    @JoinColumn(name="commodity_number",insertable = false,updatable = false,referencedColumnName = "commodity_number")
    private Set<CommodityPhoto> commodityPhotos = new HashSet<>();

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public String getPartnerDay() {
        return partnerDay;
    }

    public void setPartnerDay(String partnerDay) {
        this.partnerDay = partnerDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserSellStatus() {
        return userSellStatus;
    }

    public void setUserSellStatus(int userSellStatus) {
        this.userSellStatus = userSellStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期时间格式
        String format = dateFormat.format(time).replaceAll(".0","");//格式化一下
        this.time = format;
    }

    public Set<CommodityFile> getCommodityFiles() {
        return commodityFiles;
    }

    public void setCommodityFiles(Set<CommodityFile> commodityFiles) {
        this.commodityFiles = commodityFiles;
    }

    public Set<CommodityPhoto> getCommodityPhotos() {
        return commodityPhotos;
    }

    public void setCommodityPhotos(Set<CommodityPhoto> commodityPhotos) {
        this.commodityPhotos = commodityPhotos;
    }
}
