package com.ginko.driver.framework.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_income_expenses")
public class UserIncom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int incomeId;

    private int description;

    private int userId;

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
