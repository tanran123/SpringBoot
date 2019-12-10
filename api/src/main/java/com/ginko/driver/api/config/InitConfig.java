package com.ginko.driver.api.config;

import com.ginko.driver.api.httpClient.HttpClientUtil;
import com.ginko.driver.api.md5.Md5Util;
import com.ginko.driver.api.wx.AccessToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class InitConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
/*
        AccessToken.wxToken = "27_ZgaAPC2L0pXYIpxZn4ljXpbI1VEcV8MNeqHP6D7u8FGF_nSEUQQwJbrrZpUxwRsqAw6mhw3frNN3iNmXkNGRTjmW3VtfDApncQDJ0QeYyDLiQS0nY6wvlEicZ58oSRWSAZ6Z9Qhk7-OvPYVANMUiAIAGFN";
*/
        AccessToken.wxToken = AccessToken.InitGetWxToken();
        AccessToken.wxTicket = AccessToken.InitgetWxTicket();
        HttpClientUtil.bsv = HttpClientUtil.getCny();
        System.out.println(HttpClientUtil.bsv);
        System.out.println(AccessToken.wxToken);
        System.out.println(AccessToken.wxTicket);

    }

    public static void main(String[] args) {
        String test = Md5Util.encode("jsapi_ticket=kgt8ON7yVITDhtdwci0qeZ8AryuE6I8UQEqqoRwyb82GXijMZfb8hkmMCKkawjh8JXOIL_MwtarfgkLZmcCT6g&noncestr=6b51df01-2874-466b-8284-e8704a7ef440&timestamp=1574526265&url=https://www.timesv.com/register/inviteCode=");
        System.out.println(test.length());
        System.out.println(test);
    }
}