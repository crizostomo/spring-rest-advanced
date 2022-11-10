package com.developer.beverageapi.domain.listener;

import com.developer.beverageapi.domain.event.OrderConfirmedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class BonusClientOrderConfirmedListener {

//    @EventListener
    public void byConfirmingOrder(OrderConfirmedEvent event) {
        System.out.println("Calculating points to the client " + event.getOrder().getClient().getName());
    }
}
