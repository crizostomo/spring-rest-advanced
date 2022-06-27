package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.Notificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientActivationService {

    @Autowired
    private Notificator notificator;

    public void active(Client client) {
        client.isActive();

        notificator.notifyEmail(client, "Your record is active");
    }
}
