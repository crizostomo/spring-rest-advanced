package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.stereotype.Component;

import java.util.Locale;

public class EmailNotificator implements Notificator {

    private boolean upperCase;
    private String serverHostSmtp;

    public EmailNotificator(String serverHostSmtp) {
        this.serverHostSmtp = serverHostSmtp;
        System.out.println("Email Notificator");
    }

    @Override
    public void notifyEmail(Client client, String message){
        if (this.upperCase){
            message = message.toUpperCase(Locale.ROOT);
        }

        System.out.printf("Notifying %s through e-mail %s using SMTP %s: %s\n",
                client.getName(), client.getEmail(), this.serverHostSmtp, message);
    }

    public void setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
    }
}
