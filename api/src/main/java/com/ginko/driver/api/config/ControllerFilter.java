package com.ginko.driver.api.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.common.exception.MsgEnum;
import com.ginko.driver.framework.dao.MongoSysUserDaoImp;
import com.ginko.driver.framework.entity.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 14:32 2019/9/2
 */
@WebFilter(filterName = "bodyReaderFilter", urlPatterns = {"/px/*","/user/*"})
public class ControllerFilter implements Filter {

    @Autowired
    private MongoSysUserDaoImp mongoDBDaoImp;


    Logger logger = LoggerFactory.getLogger(ControllerFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("--------------------过滤器开始执行-------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("--------------------拦截请求-------------------");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        String body = requestWrapper.getBody();
        JSON json = JSON.parseObject(body);
        String url = httpServletRequest.getRequestURI();
        /*是否为初始化画板方法*/
        if (!url.equals("/px/getMongo")){
            /*token验证*/
            String token = ((JSONObject) json).getString("token");
            SysUser sysUser = new SysUser();
            sysUser.setUserId(((JSONObject) json).getInteger("userId"));
            sysUser = mongoDBDaoImp.queryOne(sysUser);
            if (!sysUser.getToken().equals(token)) {
                servletRequest.getRequestDispatcher("/401").forward(servletRequest, servletResponse);
            }
        }
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("--------------------过滤器销毁-------------------");
    }

    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }
}
