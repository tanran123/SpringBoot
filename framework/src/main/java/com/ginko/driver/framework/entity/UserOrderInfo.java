package com.ginko.driver.framework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_order")
public class UserOrderInfo extends CommandEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    private int userId;

    private String orderNumber;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name="commodityNumber",insertable = false,updatable = false,referencedColumnName = "commodity_number")
    private CommodityInfo commodityInfo = new CommodityInfo();

    private String consumeTime;

    private Integer serviceMoney;

    private Integer totalMoney;

    private Integer partnerUserId;

    private Integer payChannel;

    private Integer totalPayableMoney;

    private Integer realPayableMoney;

    private Integer orderStatus;

    @Transient
    @JsonIgnore
    private Integer photoMoney;

    @Transient
    @JsonIgnore
    private Integer fileMoney;

    @Transient
    @JsonIgnore
    private BigDecimal photoBsv;

    @Transient
    @JsonIgnore
    private BigDecimal fileBsv;

    @Transient
    @JsonIgnore
    private BigDecimal totalBsv;

    @Transient
    @JsonIgnore
    private BigDecimal serviceBsv;

    private int consumeType;

    @Transient
    @JsonIgnore
    private String platOrderNumber;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期时间格式
        String format = dateFormat.format(consumeTime).replaceAll(".0","");//格式化一下
        this.consumeTime = format;
    }

    public Integer getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(Integer serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }


    public CommodityInfo getCommodityInfo() {
        return commodityInfo;
    }

    public void setCommodityInfo(CommodityInfo commodityInfo) {
        this.commodityInfo = commodityInfo;
    }

    public Integer getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(Integer partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public Integer getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getTotalPayableMoney() {
        return totalPayableMoney;
    }

    public void setTotalPayableMoney(Integer totalPayableMoney) {
        this.totalPayableMoney = totalPayableMoney;
    }

    public Integer getRealPayableMoney() {
        return realPayableMoney;
    }

    public void setRealPayableMoney(Integer realPayableMoney) {
        this.realPayableMoney = realPayableMoney;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPhotoMoney() {
        return photoMoney;
    }

    public void setPhotoMoney(Integer photoMoney) {
        this.photoMoney = photoMoney;
    }

    public Integer getFileMoney() {
        return fileMoney;
    }

    public void setFileMoney(Integer fileMoney) {
        this.fileMoney = fileMoney;
    }

    public BigDecimal getPhotoBsv() {
        return photoBsv;
    }

    public void setPhotoBsv(BigDecimal photoBsv) {
        this.photoBsv = photoBsv;
    }

    public BigDecimal getFileBsv() {
        return fileBsv;
    }

    public void setFileBsv(BigDecimal fileBsv) {
        this.fileBsv = fileBsv;
    }

    public BigDecimal getTotalBsv() {
        return totalBsv;
    }

    public void setTotalBsv(BigDecimal totalBsv) {
        this.totalBsv = totalBsv;
    }

    public BigDecimal getServiceBsv() {
        return serviceBsv;
    }

    public void setServiceBsv(BigDecimal serviceBsv) {
        this.serviceBsv = serviceBsv;
    }

    public int getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(int consumeType) {
        this.consumeType = consumeType;
    }

    public String getPlatOrderNumber() {
        return platOrderNumber;
    }

    public void setPlatOrderNumber(String platOrderNumber) {
        this.platOrderNumber = platOrderNumber;
    }
}
