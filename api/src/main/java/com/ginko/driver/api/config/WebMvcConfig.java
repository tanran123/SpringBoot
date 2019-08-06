package com.ginko.driver.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*添加拦截器，排除/user的拦截*/
        registry.addInterceptor(new ControllerHandler()).addPathPatterns("/**").excludePathPatterns("/user**");
    }
}
