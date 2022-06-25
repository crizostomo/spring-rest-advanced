package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificator implements Notificator {

    public EmailNotificator() {
        System.out.println("Email Notificator");
    }

    @Override
    public void notifyEmail(Client client, String message){
        System.out.printf("Notifying %s through e-mail %: %s\n",
                client.getName(), client.getEmail(), message);
    }
}
