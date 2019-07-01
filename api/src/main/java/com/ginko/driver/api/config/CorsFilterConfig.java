package com.ginko.driver.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: tran
 * @Description: 跨域配置
 * @Date Create in 14:01 2019/7/1
 */
@Configuration
public class CorsFilterConfig {

    @Bean
    public FilterRegistrationBean corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        ArrayList<String> objects = new ArrayList<>();
        objects.add("*");
        config.setAllowedOrigins(objects);
        config.setAllowedHeaders(objects);
        config.setAllowedMethods(objects);
//        source.registerCorsConfiguration("/**", config);
        Map<String, CorsConfiguration> corsConfigurations = new HashMap<>();
        corsConfigurations.put("/**",config);
        source.setCorsConfigurations(corsConfigurations);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
