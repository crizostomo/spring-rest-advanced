package com.developer.beverageapi.service;

import com.developer.beverageapi.model.Client;
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
