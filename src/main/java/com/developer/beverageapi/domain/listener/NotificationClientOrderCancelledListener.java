package com.developer.beverageapi.domain.listener;

import com.developer.beverageapi.domain.event.OrderConfirmedEvent;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificationClientOrderCancelledListener {

    @Autowired
    private EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void byCancellingOrder(OrderConfirmedEvent event) {
        Order order = event.getOrder();

        var message = EmailService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order Cancelled")
                .body("cancelled-order.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .build();

        emailService.send(message);
    }
}
