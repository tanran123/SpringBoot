package com.ginko.driver.api.moudle;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getIncomeTop")
    public MsgConfig getIncomeTop(){
        return new MsgConfig("0",null,userService.getUserIncomeTop());
    }
}
