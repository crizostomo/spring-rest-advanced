package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class SMSNotificator implements Notificator {

//    private boolean upperCase;
//    private String serverHostSmtp;
//
//    public SMSNotificator(String serverHostSmtp) {
//        this.serverHostSmtp = serverHostSmtp;
//    }

    @Override
    public void notifyEmail(Client client, String message){
//        if (this.upperCase){
//            message = message.toUpperCase(Locale.ROOT);
//        }

        System.out.printf("Notifying %s through SMS %s using telephone : %s\n",
                client.getName(),
                client.getPhone(),
//                this.serverHostSmtp,
                message);
    }

//    public void setUpperCase(boolean upperCase) {
//        this.upperCase = upperCase;
//    }
}
