package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("sms")
@Component
public class SMSNotificator implements Notificator {

    @Override
    public void notifyEmail(Client client, String message){

        System.out.printf("Notifying %s through SMS %s using telephone : %s\n",
                client.getName(),
                client.getPhone(),
                message);
    }
}
