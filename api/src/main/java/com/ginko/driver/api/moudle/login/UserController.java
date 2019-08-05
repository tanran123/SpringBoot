package com.ginko.driver.api.moudle.login;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import com.ginko.driver.framework.dao.MongoDBDaoImp;
import com.ginko.driver.framework.entity.BoxEntity;
import com.ginko.driver.framework.entity.SysUser;
import com.ginko.driver.framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private MongoDBDaoImp mongoDBDaoImp;

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
        /*从MONGODB中获取ICON*/
        SysUser book = new SysUser();
        book.setUserName(sysUser.getUserName());
        List<SysUser> boxEntities = mongoDBDaoImp.queryList(book);
        if (boxEntities.size() > 0) {
            sysUser1.setIcon(boxEntities.get(0).getIcon());
        }
        return new MsgConfig("SUCCESS", "", sysUser1);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public MsgConfig register(@RequestBody SysUser sysUser) {
        SysUser sysUser1 = sysUserService.findByuserName(sysUser.getUserName());
        if (sysUser1 != null) {
            return new MsgConfig("ERROR", MsgEnum.USERALREADYEXIST.getDesc());
        }
        SysUser book = new SysUser();
        book.setUserName(sysUser.getUserName());
        book.setIcon(sysUser.getIcon());
        mongoDBDaoImp.save(book);
        /*将ICON存在mongodb中 --优化性能*/
        sysUser.setIcon("");
        sysUserService.saveUser(sysUser);
        return new MsgConfig("SUCCESS", "", null);
    }
}
