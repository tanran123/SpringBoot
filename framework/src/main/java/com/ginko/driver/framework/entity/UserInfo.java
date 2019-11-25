package com.ginko.driver.framework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserInfo extends CommandEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;

    @Transient
    @JsonIgnore
    private String passWord;

    private String nickName;


    @Transient
    @JsonIgnore
    private String phone;


    @Transient
    @JsonIgnore
    private String email;


    @Transient
    @JsonIgnore
    private String idCard;


    @Transient
    @JsonIgnore
    private String registerTime;

    @Transient
    @JsonIgnore
    private String firstName;

    @Transient
    @JsonIgnore
    private String givenTime;

    @Transient
    @JsonIgnore
    private String realName;

    @Transient
    @JsonIgnore
    private String sex;

    @Transient
    @JsonIgnore
    private String avatar;

    @Transient
    @JsonIgnore
    private String realNameAuthority;

    @Transient
    @JsonIgnore
    private String birthDay;

    @Transient
    @JsonIgnore
    private String nation;

    @Transient
    @JsonIgnore
    private String province;

    @Transient
    @JsonIgnore
    private String city;

    @Transient
    @JsonIgnore
    private String alipayUserId;

    @Transient
    @JsonIgnore
    private String weixinAppUserId;

    @Transient
    @JsonIgnore
    private BigDecimal balance;

    @Transient
    @JsonIgnore
    private String privateKey;

    @Transient
    @JsonIgnore
    private String userWord;

    @Transient
    @JsonIgnore
    private String moneyButtonId;

    @Transient
    @JsonIgnore
    private String fileLocation;

    @Transient
    @JsonIgnore
    private int userStatus;

    @Transient
    @JsonIgnore
    private String inviteCode;

    @Transient
    @JsonIgnore
    private BigDecimal bsvBalance;

    private String wxOpenId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期时间格式
        String format = dateFormat.format(registerTime).replaceAll(".0","");//格式化一下
        this.registerTime = format;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGivenTime() {
        return givenTime;
    }

    public void setGivenTime(String givenTime) {
        this.givenTime = givenTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealNameAuthority() {
        return realNameAuthority;
    }

    public void setRealNameAuthority(String realNameAuthority) {
        this.realNameAuthority = realNameAuthority;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期时间格式
        String format = dateFormat.format(birthDay).replaceAll(".0","");//格式化一下
        this.birthDay = format;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public String getWeixinAppUserId() {
        return weixinAppUserId;
    }

    public void setWeixinAppUserId(String weixinAppUserId) {
        this.weixinAppUserId = weixinAppUserId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getUserWord() {
        return userWord;
    }

    public void setUserWord(String userWord) {
        this.userWord = userWord;
    }

    public String getMoneyButtonId() {
        return moneyButtonId;
    }

    public void setMoneyButtonId(String moneyButtonId) {
        this.moneyButtonId = moneyButtonId;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public BigDecimal getBsvBalance() {
        return bsvBalance;
    }

    public void setBsvBalance(BigDecimal bsvBalance) {
        this.bsvBalance = bsvBalance;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }
}
