package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@NotifierType(UrgencyLevel.NORMAL)
@Component
public class EmailNotificator implements Notificator {

    @Value("${notifier.email.host-server}")
    private String host;

    @Value("${notifier.email.server-port}")
    private Integer port;

    @Override
    public void notifyEmail(Client client, String message){

        System.out.printf("Notifying %s through e-mail %s : %s\n",
                client.getName(),
                client.getEmail(),
                message);
    }
}
