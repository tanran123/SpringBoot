package com.ginko.driver.api.moudle.error;

import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:43 2019/9/2
 */
@RestController
public class ErrorController {

    @RequestMapping(value = "/401",method = RequestMethod.POST)
    public MsgConfig post404(){
        return new MsgConfig(401, MsgEnum.NOROLEAUTH.getDesc(), null);
    }
}
