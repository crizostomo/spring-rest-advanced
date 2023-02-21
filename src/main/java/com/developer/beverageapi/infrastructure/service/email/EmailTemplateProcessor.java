package com.developer.beverageapi.infrastructure.service.email;

import com.developer.beverageapi.domain.service.EmailService;
import com.developer.beverageapi.infrastructure.service.storage.StorageException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class EmailTemplateProcessor {

    @Autowired
    private Configuration freemarkerConfig;

    protected String processTemplate(EmailService.Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new StorageException("It was not possible to build the email template", e);
        }
    }
}
