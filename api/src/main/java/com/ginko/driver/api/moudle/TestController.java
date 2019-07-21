package com.ginko.driver.api.moudle;


import com.ginko.driver.common.entity.MsgConfig;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class TestController {


    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public MsgConfig getUserInfo(){
       return new MsgConfig(200,"返回成功",200);
    }
}
