package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.EmailNotificator;
import com.developer.beverageapi.notification.Notificator;
import org.springframework.stereotype.Component;

@Component
public class ClientActivationService {

    private Notificator notificator;

    public ClientActivationService(EmailNotificator notificator) {
        this.notificator = notificator;

        System.out.println("ClientActivationService: " + notificator);
    }

    public void active(Client client){
        client.isActive();

        notificator.notifyEmail(client, "Your record is active");
    }
}
