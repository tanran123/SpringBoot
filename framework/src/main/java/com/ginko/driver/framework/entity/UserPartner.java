package com.ginko.driver.framework.entity;


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
    private BigDecimal partnerIcome;
    private int partnerStatus;
    private String butDateTime;
    private String sellTime;

    private String partnerDay;

    private int paymentStatus;

    private String orderId;

    private String buyPrice;

    private String sellPrice;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="partnerId",insertable = false,updatable = false)
    @Where(clause = "userId=partnerUserId")
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

    public BigDecimal getPartnerIcome() {
        return partnerIcome;
    }

    public void setPartnerIcome(BigDecimal partnerIcome) {
        this.partnerIcome = partnerIcome;
    }

    public int getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(int partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public String getButDateTime() {
        return butDateTime;
    }

    public void setButDateTime(String butDateTime) {
        this.butDateTime = butDateTime;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
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

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
