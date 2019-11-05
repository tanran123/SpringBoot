package com.ginko.driver.api.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:32 2019/9/2
 */
public class ControllerFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(ControllerFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("--------------------拦截请求-------------------");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        String body = requestWrapper.getBody();
        logger.info(body);
        JSON json = JSON.parseObject(body);
       /* *//*是否为初始化画板方法*//*
        if (url.indexOf("/px/getMongo")==-1){
            *//*token验证*//*
            String token = ((JSONObject) json).getString("token");
            SysUser sysUser = new SysUser();
            sysUser.setUserId(((JSONObject) json).getInteger("userId"));
            sysUser = mongoDBDaoImp.queryOne(sysUser);
            if (!token.equals(sysUser.getToken())) {
                servletRequest.getRequestDispatcher("/401").forward(servletRequest, servletResponse);
            }
        }*/
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("--------------------过滤器销毁-------------------");
    }

}
