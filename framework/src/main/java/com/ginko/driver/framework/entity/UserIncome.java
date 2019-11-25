package com.ginko.driver.framework.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_income_expenses")
public class UserIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int incomeId;

    private int description;

    private int userId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name="userId",insertable = false,updatable = false,referencedColumnName = "userId")
    private UserInfo userInfo = new UserInfo();

    private BigDecimal money;

    private String orderCode;

    private int type;

    private String time;


    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
