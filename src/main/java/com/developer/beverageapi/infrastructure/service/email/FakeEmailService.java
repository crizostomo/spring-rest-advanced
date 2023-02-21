package com.developer.beverageapi.infrastructure.service.email;

import com.developer.beverageapi.domain.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeEmailService implements EmailService {

    @Autowired
    private EmailTemplateProcessor emailTemplateProcessor;

    @Override
    public void send(Message message) {
        String body = emailTemplateProcessor.processTemplate(message);

        log.info("[FAKE E-MAIL] TO: {}\n{]", message.getRecipients(), body);
    }
}
