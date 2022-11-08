package com.developer.beverageapi.infrastructure.service.email;

import com.developer.beverageapi.core.email.EmailProperties;
import com.developer.beverageapi.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EmailService emailService() {

        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEmailService();
            case SMTP:
                return new SmtpEmailService();
            case SANDOX:
                return new SandboxEmailService();
            default:
                return null;
        }
    }
}
