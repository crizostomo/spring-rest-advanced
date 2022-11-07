package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowOrderService {

    @Autowired
    private OrderIssuingRegistrationService orderIssuing;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void confirm(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.confirm();

        var message = EmailService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order Confirmed")
                .body("order-confirmed.html")
                .variable("order", order)
                .recipient(order.getClient().getEmail())
                .recipient("test@gmail.com")
                .build();
    }

    @Transactional
    public void delivery(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.deliver();
    }

    @Transactional
    public void cancel(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.cancel();
    }
}
