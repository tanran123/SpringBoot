package com.ginko.driver.api.moudle.wx;

import com.ginko.driver.api.md5.Md5Util;
import com.ginko.driver.api.wx.AccessToken;
import com.ginko.driver.api.wx.WxConfig;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.tolls.TokenTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RequestMapping("/wx")
@RestController
public class WxController {

    @RequestMapping("/getConfig")
    public MsgConfig getConfig(@RequestBody WxConfig wxConfig){
        String url = wxConfig.getUrl();
        String noncestr = UUID.randomUUID().toString(); ;
        String jsapi_ticket = AccessToken.wxTicket;
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        String sin = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp
                +"&url="+url;
        String sh1 = Md5Util.encode(sin);

        System.out.println("string1:"+sin);
        System.out.println("------------------------");
        System.out.println("sh1:"+sh1);
        wxConfig.setTimestamp(timestamp);
        wxConfig.setSignature(sh1);
        wxConfig.setNoncestr(noncestr);
        return new MsgConfig("0","ok",wxConfig);
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void GetWxToken(){
        //获取微信授权码
        AccessToken.wxToken =AccessToken.InitGetWxToken();
        //获取微信ticket
        AccessToken.wxTicket= AccessToken.InitgetWxTicket();
        System.out.println(AccessToken.wxToken);
        System.out.println(AccessToken.wxTicket);
    }
}
