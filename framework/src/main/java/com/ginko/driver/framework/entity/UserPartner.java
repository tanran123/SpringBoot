package com.ginko.driver.framework.entity;


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

    private int sellStatus;

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

    public int getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(int sellStatus) {
        this.sellStatus = sellStatus;
    }
}
