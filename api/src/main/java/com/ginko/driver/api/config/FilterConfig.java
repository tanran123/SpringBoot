package com.ginko.driver.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/*@Configuration
public class FilterConfig implements WebMvcConfigurer {
    @Bean
    public Filter newuserFilter() {
        return new ControllerFilter();
    }

    @Bean
    public FilterRegistrationBean userFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("ControllerFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("filter");
        registration.setOrder(1);
        return registration;
    }

   *//* @Bean
    public FilterRegistrationBean pxFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("newpxFilter"));
        registration.addUrlPatterns("/*");
        registration.setName("newpxFilter");
        registration.setOrder(2);
        return registration;
    }*//*
}*/
