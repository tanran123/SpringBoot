package com.ginko.driver.api.moudle.login;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import com.ginko.driver.framework.dao.MongoSysUserDaoImp;
import com.ginko.driver.framework.entity.LogInfo;
import com.ginko.driver.framework.entity.SysUser;
import com.ginko.driver.framework.service.LogInfoService;
import com.ginko.driver.framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private MongoSysUserDaoImp mongoDBDaoImp;

    @Autowired
    private LogInfoService logInfoService;

    @RequestMapping(value = "/updateToken", method = RequestMethod.POST)
    public MsgConfig register(@RequestBody SysUser sysUser) {
        SysUser sysUserNew = new SysUser();
        sysUserNew.setUserId(sysUser.getUserId());
        SysUser sysUser1 = mongoDBDaoImp.queryOne(sysUserNew);
        SysUser sysUser11 = sysUserService.findByUserId(sysUser.getUserId());
        //第一次登陆记录用户
        if(sysUser1 ==null){
            mongoDBDaoImp.save(sysUser);
        }
        if (sysUser11 ==null){
            sysUserService.saveUser(sysUser);
        }
        else{
            mongoDBDaoImp.updateFirst(sysUser1,sysUser);
        }
        return new MsgConfig("200", "", null);
    }

    @RequestMapping(value = "/401", method = RequestMethod.POST)
    public MsgConfig error401(@RequestBody SysUser sysUser) {
        return new MsgConfig("401", MsgEnum.NOAUTH.getDesc(), null);
    }

    @RequestMapping(value = "/getLogInfoList", method = RequestMethod.POST)
    public MsgConfig getLogInfoList(@RequestBody LogInfo logInfo){
        List<LogInfo> list = logInfoService.findByUserIdAndReadStatus(logInfo);
        return new MsgConfig("200",null,list);
    }


    @RequestMapping(value = "/updateLogInfoStatus", method = RequestMethod.POST)
    public MsgConfig updateLogInfoStatus(@RequestBody LogInfo logInfo){
         logInfoService.updateLogInfo(logInfo);
        return new MsgConfig("200",null,null);
    }
}
