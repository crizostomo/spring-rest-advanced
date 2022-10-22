package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusOrderService {

    @Autowired
    private OrderIssuingRegistrationService orderIssuing;

    @Transactional
    public void confirm(String codeOrder) {
        Order order = orderIssuing.searchOrFail(codeOrder);
        order.confirm();
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