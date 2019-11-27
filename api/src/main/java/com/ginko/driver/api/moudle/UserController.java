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
        List<Map<String,Object>> list1 = new ArrayList<>();
        for (Map<String,Object> map :list){
            if (map.get("wx_open_id")!=null){
                Map<String,Object> mapNew = new HashMap<>();
                JSONObject jsonObject = HttpClientUtil.getWxInfo(map.get("wx_open_id").toString());
                mapNew.putAll(map);
                mapNew.remove("wx_open_id");
                mapNew.put("headImg",jsonObject.getString("headimgurl"));
                mapNew.put("wxName",jsonObject.getString("nickname"));
                list1.add(mapNew);
            }
            else{
                list1.add(map);
            }
        }
        return new MsgConfig("0",null,list1);
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
        JSONObject jsonpObject = HttpClientUtil.getWxUserInfoTokenAndOpenId(userInfo.getCode());
        String openId = jsonpObject.getString("openid");
        if (openId!=null){
            JSONObject wxUserInfo = HttpClientUtil.getWxInfo(openId);
            String nickName = wxUserInfo.getString("nickname");
            String headUrl = wxUserInfo.getString("headimgurl");
            userInfo.setWxHeadImgUrl(headUrl);
            userInfo.setWxOpenId(openId);
            userInfo.setNickName(nickName);
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
            UserInfo userInfoQuery = userService.findByOpenId(userInfo);
            if (userInfoQuery==null){
                return new MsgConfig("102","用户未注册",userInfo.getCode());
            }
            else{
                return new MsgConfig("101","OK",userInfoQuery);
            }
        }
        else{
            return new MsgConfig("103","微信授权失败",null);
        }
    }
}
