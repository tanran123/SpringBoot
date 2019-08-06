package com.ginko.driver.api.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class ControllerHandler extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(ControllerHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(request.getRemoteAddr());
        logger.info(request.getRequestURL().toString());
        String token = request.getHeader("token");
        logger.info(token);
        return true;
    }
}
