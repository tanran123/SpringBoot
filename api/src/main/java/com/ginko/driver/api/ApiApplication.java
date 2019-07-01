package com.ginko.driver.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//扫描所有类
@ComponentScan(value = "com.ginko.driver")
/*//扫描JpaRepositories包
@EnableJpaRepositories(basePackages = "com.travelsky")*/
//扫描JpaRepositories所使用entity
@EntityScan(basePackages = "com.ginko.driver")
//配置多数据源
@EnableJpaRepositories
//开启定时处理器
@EnableScheduling
public class ApiApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
