package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificator {

    public void notifyEmail(Client client, String message){
        System.out.printf("Notifying %s through e-mail %: %s\n",
                client.getName(), client.getEmail(), message);
    }
}
