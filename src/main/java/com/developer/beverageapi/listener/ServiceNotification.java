package com.developer.beverageapi.listener;

import com.developer.beverageapi.notification.Notificator;
import com.developer.beverageapi.notification.NotifierType;
import com.developer.beverageapi.notification.UrgencyLevel;
import com.developer.beverageapi.service.ActivatedClientEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceNotification {

    @NotifierType(UrgencyLevel.NORMAL)
    @Autowired
    private Notificator notificator;


    @EventListener
    public void activatedClientListener(ActivatedClientEvent event){
        notificator.notifyEmail(event.getClient(), "Your profile is now Active!");
    }
}
