package com.ginko.driver.api.moudle.login;


import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import com.ginko.driver.framework.entity.SysUser;
import com.ginko.driver.framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public MsgConfig login(@RequestBody SysUser data){
       SysUser sysUser = sysUserService.findByuserNameAndPassword(data.getUserName(),data.getPassword());

       if (sysUser==null){
           return new MsgConfig("ERROR",MsgEnum.USERNAMEANDPASSWORDERROR.getDesc());
       }

        return new MsgConfig("SUCCESS","",sysUser);
    }
}
