package com.ginko.driver.framework.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 合伙人
 */
@Entity
@Table(name = "partner")
public class Partner extends CommandEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int partnerId;
    private int partnerUserId;
    private String partnerDay;
    private String partnerNation;
    private BigDecimal price;
    private int viewCount;
    private int sellStatus;
    @JsonIgnore
    private int lockStatus;
    @JsonIgnore
    private String lockTime;
    @JsonIgnore
    private int lockUserId;

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(int partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getPartnerDay() {
        return partnerDay;
    }

    public void setPartnerDay(String partnerDay) {
        this.partnerDay = partnerDay;
    }

    public String getPartnerNation() {
        return partnerNation;
    }

    public void setPartnerNation(String partnerNation) {
        this.partnerNation = partnerNation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(int sellStatus) {
        this.sellStatus = sellStatus;
    }

    public int getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }

    public int getLockUserId() {
        return lockUserId;
    }

    public void setLockUserId(int lockUserId) {
        this.lockUserId = lockUserId;
    }
}
