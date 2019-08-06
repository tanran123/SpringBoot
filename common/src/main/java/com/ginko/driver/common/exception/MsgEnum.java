package com.ginko.driver.common.exception;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:39 2019/7/22
 */
public enum MsgEnum {
    LOGINSUCCESS("SUCCESS","登陆成功"),
    USERNOTEXIST("ERROR","用户不存在"),
    USERALREADYEXIST("ERROR","用户已经存在"),
    NOAUTH("ERROR","身份认证已过期，请重新登陆"),
    USERNAMEANDPASSWORDERROR("ERROR","用户名或密码错误"),
    SYSTEMERROR("ERROR","系统错误");
    private String code;
    private String desc;

    private MsgEnum(String code, String desc) {
        this.setCode(code);
        this.setDesc(desc);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
