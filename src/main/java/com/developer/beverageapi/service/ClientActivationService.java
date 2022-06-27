package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.Notificator;
import com.developer.beverageapi.notification.NotifierType;
import com.developer.beverageapi.notification.UrgencyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClientActivationService {

    @NotifierType(UrgencyLevel.URGENT)
    @Autowired
    private Notificator notificator;

    public void active(Client client) {
        client.isActive();

        notificator.notifyEmail(client, "Your record is active");
    }
}
