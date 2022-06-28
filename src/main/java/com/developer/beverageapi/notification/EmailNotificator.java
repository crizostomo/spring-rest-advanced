package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@NotifierType(UrgencyLevel.NORMAL)
@Component
public class EmailNotificator implements Notificator {

    public EmailNotificator() {
        System.out.println("Real EmailNotificator");
    }

    @Override
    public void notifyEmail(Client client, String message){

        System.out.printf("Notifying %s through e-mail %s : %s\n",
                client.getName(),
                client.getEmail(),
                message);
    }
}
