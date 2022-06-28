package com.developer.beverageapi.listener;

import com.developer.beverageapi.service.ActivatedClientEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NFEmissionService {

    @EventListener
    public void activatedClientListener(ActivatedClientEvent event){
        System.out.println("Emiting NF for client: " + event.getClient().getName());
    }
}
