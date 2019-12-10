package com.ginko.driver.api.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ginko.driver.api.httpClient.HttpClientUtil;
import org.springframework.scheduling.annotation.Scheduled;

public class AccessToken {

    public static String wxToken;

    public static String wxTicket;

    public static String InitGetWxToken(){
        JSONObject j = (JSONObject) HttpClientUtil.getWxToken();
        return j.getString("access_token");
    }

    public static String InitgetWxTicket(){
        JSONObject j = (JSONObject) HttpClientUtil.getWxTicket(wxToken);
        return j.getString("ticket");
    }
}