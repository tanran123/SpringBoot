package com.ginko.driver.api.config;

import com.ginko.driver.api.webSocket.CustomerWebSoket;
import com.ginko.driver.framework.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 15:11 2019/8/17
 */

@Configuration
public class WebSocketConfig {
/**
     * 注入ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     *
     * @return*/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    @Autowired
    public void setService(PartnerService partnerService){ CustomerWebSoket.partnerService = partnerService; }
}

