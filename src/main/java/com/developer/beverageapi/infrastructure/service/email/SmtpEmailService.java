package com.developer.beverageapi.infrastructure.service.email;

import com.developer.beverageapi.core.email.EmailProperties;
import com.developer.beverageapi.domain.service.EmailService;
import com.developer.beverageapi.infrastructure.service.storage.StorageException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//@Service
public class SmtpEmailService implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = getMimeMessage(message);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new StorageException("It was not possible to send the email", e);
        }
    }

    protected MimeMessage getMimeMessage(Message message) throws MessagingException {
        String body = processTemplate(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(emailProperties.getRecipient());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);
        return mimeMessage;
    }

    protected String processTemplate(Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new StorageException("It was not possible to build the email template", e);
        }
    }
}