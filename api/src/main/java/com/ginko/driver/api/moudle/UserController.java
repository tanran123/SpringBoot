package com.ginko.driver.api.moudle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.ginko.driver.api.httpClient.HttpClientUtil;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.tolls.TokenTools;
import com.ginko.driver.framework.entity.UserInfo;
import com.ginko.driver.framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getIncomeTop")
    public MsgConfig getIncomeTop(){
        List<Map<String,Object>> list =  userService.getUserIncomeTop();
        return new MsgConfig("0",null,list);
    }

    /**
     * 更新用户微信OPENID等
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping("updateWxInfo")
    public MsgConfig updateWxInfo(@RequestBody UserInfo userInfo, HttpServletRequest request){
        try {
            Integer userId = TokenTools.getUserIdFromToken(request.getHeader("Authorization"));
            if (userId!=userInfo.getUserId()){
                return new MsgConfig("401","权限不足",null);
            }
            userInfo.setUserId(userId);
        }
        catch (Exception e){
            return new MsgConfig("401","权限不足",null);
        }
        String openId =userInfo.getOpenId();
        if (openId!=null){
            System.out.println(userInfo.getOpenId());
            userService.updateWxInfo(userInfo);
            return new MsgConfig("0","ok",userInfo);
        }
        else{
            return new MsgConfig("-1","系统异常",null);
        }
    }


    /**
     * 微信登陆
     * @return
     */
    @RequestMapping("wxLogin")
    public MsgConfig wxLogin(@RequestBody UserInfo userInfo){
        JSONObject jsonpObject = HttpClientUtil.getWxUserInfoTokenAndOpenId(userInfo.getCode());
        String openId = jsonpObject.getString("openid");
        userInfo.setWxOpenId(openId);
        if (openId!=null){
            JSONObject json = (JSONObject)HttpClientUtil.httpPost("https://www.timesv.com/timesv/user/v1/wechat/bind/status","{\"openId\":\""+openId+"\"}","123");
            if (json.getInteger("responseCode")==210){
                String token = jsonpObject.getString("access_token");
                JSON jsonInfo = HttpClientUtil.httpGetForJSON("https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId+"&lang=zh_CN");
                System.out.println("--------------------------\n"+jsonInfo+"\n--------------------------\n");
                return new MsgConfig("102","用户未注册",jsonInfo);
            }
            else if (json.getInteger("responseCode")==0){
                return new MsgConfig("101","OK",json.getJSONObject("data"));
            }
            else{
                return new MsgConfig("102","用户未注册",openId);
            }
        }
        else{
            return new MsgConfig("103","微信授权失败",null);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "{\"country\":\"\",\"province\":\"\",\"city\":\"\",\"openid\":\"ojabuwxtLiO5uarde57Umx1uzu1g\",\"sex\":1,\"nickname\":\"è°\u00ADç\\u0084¶\",\"headimgurl\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/8m96uYNX9WfhN9hicLe24VCgN5EE1ypx97aLXicD5DSkQIVzjjz87C69cpCx2MgUHzdwzQZVGKmAHNPibAXBHmicsg/132\",\"language\":\"zh_CN\",\"privilege\":[]}";
        System.out.println(  URLEncoder.encode("è°\u00ADç\\u0084¶","utf-8"));

    }
}
