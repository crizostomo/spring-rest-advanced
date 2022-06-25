package com.developer.beverageapi.config;

import com.developer.beverageapi.notification.Notificator;
import com.developer.beverageapi.service.ClientActivationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ClientActivationService clientActivationService(Notificator notificator){
        return new ClientActivationService(notificator);
    }
}
