package com.developer.beverageapi.notification;

import com.developer.beverageapi.model.Client;

public interface Notificator {
    void notifyEmail(Client client, String message);
}
