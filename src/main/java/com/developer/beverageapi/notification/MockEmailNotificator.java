package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@NotifierType(UrgencyLevel.NORMAL)
@Component
public class MockEmailNotificator implements Notificator {

    public MockEmailNotificator() {
        System.out.println("Mock MockEmailNotificator");
    }

    @Override
    public void notifyEmail(Client client, String message){

        System.out.printf("MOCK: Notifying %s would be sent through e-mail %s : %s\n",
                client.getName(),
                client.getEmail(),
                message);
    }
}
