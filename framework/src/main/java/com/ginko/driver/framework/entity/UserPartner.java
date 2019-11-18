package com.ginko.driver.framework.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_partner")
public class UserPartner extends CommandEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;
    private int partnerId;
    private BigDecimal partnerIncome;
    private String buyDatetime;
    private String sellDatetime;
    private int partnerStatus;
    private String partnerDay;
    private int paymentStatus;
    private String orderId;

    private BigDecimal buyPrice;

    private BigDecimal sellPrice;

    private int sellUserId;


    private BigDecimal buyBsvPrice;


    private BigDecimal sellBsvPrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="partnerId",insertable = false,updatable = false)
    private Partner partner;

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

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }


    public int getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(int partnerStatus) {
        this.partnerStatus = partnerStatus;
    }


    public String getPartnerDay() {
        return partnerDay;
    }

    public void setPartnerDay(String partnerDay) {
        this.partnerDay = partnerDay;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public BigDecimal getPartnerIncome() {
        return partnerIncome;
    }

    public void setPartnerIncome(BigDecimal partnerIncome) {
        this.partnerIncome = partnerIncome;
    }

    public String getBuyDatetime() {
        return buyDatetime;
    }

    public void setBuyDatetime(String buyDatetime) {
        this.buyDatetime = buyDatetime;
    }

    public String getSellDatetime() {
        return sellDatetime;
    }

    public void setSellDatetime(String sellDatetime) {
        this.sellDatetime = sellDatetime;
    }

    public int getSellUserId() {
        return sellUserId;
    }

    public void setSellUserId(int sellUserId) {
        this.sellUserId = sellUserId;
    }


    public BigDecimal getBuyBsvPrice() {
        return buyBsvPrice;
    }

    public void setBuyBsvPrice(BigDecimal buyBsvPrice) {
        this.buyBsvPrice = buyBsvPrice;
    }

    public BigDecimal getSellBsvPrice() {
        return sellBsvPrice;
    }

    public void setSellBsvPrice(BigDecimal sellBsvPrice) {
        this.sellBsvPrice = sellBsvPrice;
    }
}
