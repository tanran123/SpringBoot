package com.ginko.driver.api.moudle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.ginko.driver.api.httpClient.HttpClientUtil;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.tolls.TokenTools;
import com.ginko.driver.framework.entity.UserInfo;
import com.ginko.driver.framework.service.UserService;
import org.apache.catalina.User;
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
            UserInfo userInfo1 = userService.findByOpenId(openId);
            if (userInfo1!=null){
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
                String token = jsonpObject.getString("access_token");
                JSON jsonInfo = HttpClientUtil.httpGetForJSON("https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId+"&lang=zh_CN");
                System.out.println("--------------------------\n"+jsonInfo+"\n--------------------------\n");
                return new MsgConfig("102","用户未注册",jsonInfo);
            }
        }
        else{
            return new MsgConfig("103","微信授权失败",null);
        }
    }

    @RequestMapping("dotWalletLogin")
    public MsgConfig dotWalletLogin(@RequestBody UserInfo userInfo){
        JSONObject jsonObject = (JSONObject) HttpClientUtil.httpPost("https://www.ddpurse.com/openapi/access_token","{\"app_id\":\"a55c1e2b49bab9f00cea89a3266dc8d3\",\"secret\":\"babe5bc5f69e93fd619cd5aec22f028b\",\"code\":\""+userInfo.getCode()+"\"}");
        //判断是否code
        if (jsonObject.getInteger("code")==0){
            //获取access_token
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            String accessToken = jsonObject1.getString("access_token");
            JSONObject dotWalletInfo = HttpClientUtil.httpGet("https://www.ddpurse.com/openapi/get_user_info?access_token="+accessToken);
            if (dotWalletInfo.getInteger("code")==0){
                UserInfo userInfo1 = userService.findByDotWalletOpenId(dotWalletInfo.getJSONObject("data").getString("user_open_id"));
                 //查看是否存在改用户
                 if (userInfo1==null){
                     return new MsgConfig("101","请绑定手机号",dotWalletInfo.getJSONObject("data"));
                 }
                 //不存在
                 else{
                     //todo调用生成TOKEN接口
                     return new MsgConfig("101","请绑定手机号",dotWalletInfo.getJSONObject("data"));
                 }
            }
            else{
                return new MsgConfig(dotWalletInfo.getString("code"),dotWalletInfo.getString("msg"),null);
            }
        }
        else{
            return new MsgConfig(jsonObject.getString("code"),jsonObject.getString("msg"),null);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "{\"country\":\"\",\"province\":\"\",\"city\":\"\",\"openid\":\"ojabuwxtLiO5uarde57Umx1uzu1g\",\"sex\":1,\"nickname\":\"è°\u00ADç\\u0084¶\",\"headimgurl\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/8m96uYNX9WfhN9hicLe24VCgN5EE1ypx97aLXicD5DSkQIVzjjz87C69cpCx2MgUHzdwzQZVGKmAHNPibAXBHmicsg/132\",\"language\":\"zh_CN\",\"privilege\":[]}";
        System.out.println(  URLEncoder.encode("è°\u00ADç\\u0084¶","utf-8"));

    }
}
