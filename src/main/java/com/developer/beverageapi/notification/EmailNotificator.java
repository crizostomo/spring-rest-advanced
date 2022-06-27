package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Qualifier("email")
@Component
public class EmailNotificator implements Notificator {

    @Override
    public void notifyEmail(Client client, String message){

        System.out.printf("Notifying %s through e-mail %s : %s\n",
                client.getName(),
                client.getEmail(),
                message);
    }
}
