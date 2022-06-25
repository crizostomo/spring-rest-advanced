package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.Notificator;

public class ClientActivationService {

    private Notificator notificator;

    public ClientActivationService(Notificator notificator) {
        this.notificator = notificator;

        System.out.println("ClientActivationService: " + notificator);
    }

    public void active(Client client){
        client.isActive();

        notificator.notifyEmail(client, "Your record is active");
    }
}
