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

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:45 2019/7/22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MsgConfig login(@RequestBody SysUser sysUser) {
        SysUser sysUser1 = sysUserService.findByuserName(sysUser.getUserName());
        if (sysUser1 == null) {
            return new MsgConfig("ERROR", MsgEnum.USERNOTEXIST.getDesc());
        }
        /*用户名或密码错误*/
        if (!sysUser1.getPassword().equals(sysUser.getPassword())) {
            return new MsgConfig("ERROR", MsgEnum.USERNAMEANDPASSWORDERROR.getDesc());
        }
        return new MsgConfig("SUCCESS", "", sysUser1);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public MsgConfig register(@RequestBody SysUser sysUser) {
        SysUser sysUser1 = sysUserService.findByuserName(sysUser.getUserName());
        if (sysUser1 != null) {
            return new MsgConfig("ERROR", MsgEnum.USERALREADYEXIST.getDesc());
        }
        sysUserService.saveUser(sysUser);
        return new MsgConfig("SUCCESS", "", null);
    }
}
