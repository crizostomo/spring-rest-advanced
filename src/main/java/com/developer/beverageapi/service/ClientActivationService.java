package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
import com.developer.beverageapi.notification.Notificator;
import com.developer.beverageapi.notification.NotifierType;
import com.developer.beverageapi.notification.UrgencyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ClientActivationService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void active(Client client) {
        client.isActive();

        eventPublisher.publishEvent(new ActivatedClientEvent(client));
    }
}
