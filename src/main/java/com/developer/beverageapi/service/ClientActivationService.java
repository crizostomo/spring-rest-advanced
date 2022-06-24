package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.EmailNotificator;
import org.springframework.stereotype.Component;

@Component
public class ClientActivationService {

    private EmailNotificator notificator;

    public void active(Client client){
        client.isActive();

        notificator.notifyEmail(client, "Your record is active");
    }
}
