package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.Notificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientActivationService {

    @Autowired
    private List<Notificator> notificators;

    public void active(Client client) {
        client.isActive();

        for(Notificator notificator : notificators) {
            notificator.notifyEmail(client, "Your record is active");
        }
    }
}
