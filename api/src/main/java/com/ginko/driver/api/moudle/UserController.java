package com.ginko.driver.api.moudle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ginko.driver.api.httpClient.HttpClientUtil;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
