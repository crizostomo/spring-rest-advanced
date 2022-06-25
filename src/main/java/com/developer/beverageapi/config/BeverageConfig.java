package com.developer.beverageapi.config;

import com.developer.beverageapi.notification.EmailNotificator;
import com.developer.beverageapi.service.ClientActivationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class BeverageConfig {

    @Bean
    public EmailNotificator emailNotificator(){
        EmailNotificator notificator = new EmailNotificator("smtp.beveragemail.com.br");
        notificator.setUpperCase(true);

        return notificator;
    }

    @Bean
    public ClientActivationService clientActivationService(){
        return new ClientActivationService(emailNotificator());
    }
}
