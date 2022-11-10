package com.developer.beverageapi.domain.listener;

import com.developer.beverageapi.domain.event.OrderConfirmedEvent;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationClientOrderConfirmedListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    public void byConfirmingOrder(OrderConfirmedEvent event) {
        Order order = event.getOrder();

        var message = EmailService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order Confirmed")
                .body("confirmed-order.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .build();

        emailService.send(message);
    }
}
