package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.Notificator;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientActivationService {

    @Autowired(required = false)
    private Notificator notificator;

    public ClientActivationService(Notificator notificator) {
        this.notificator = notificator;
    }

    public void active(Client client) {
        client.isActive();

        if (notificator != null) {

            notificator.notifyEmail(client, "Your record is active");
        } else {
            System.out.println("There is no notificator, but client has been activated");
        }
    }
}
