package com.developer.beverageapi.infrastructure.service.email;

import com.developer.beverageapi.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandboxEmailService extends SmtpEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage getMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = super.getMimeMessage(message);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(emailProperties.getSandbox().getRecipient());

        return mimeMessage;
    }
}
