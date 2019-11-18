package com.ginko.driver.framework.entity;


/**
 * @Auther: tran
 * @Date: 2019/11/5
 * @Description:
 */
public class WebSocketReturnType {
    private String orderCode;

    private boolean paymentStatus;

    private String paymentType;


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
