package com.developer.beverageapi.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailService extends SmtpEmailService {

    @Override
    public void send(Message message) {
        String body = processTemplate(message);

        log.info("[FAKE E-MAIL] TO: {}\n{]", message.getRecipients(), body);
    }
}
