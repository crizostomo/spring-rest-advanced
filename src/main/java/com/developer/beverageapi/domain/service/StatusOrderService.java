package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class StatusOrderService {

    @Autowired
    private OrderIssuingRegistrationService orderIssuing;

    @Transactional
    public void confirm(Long orderId) {
        Order order = orderIssuing.searchOrFail(orderId);
        order.confirm();
    }

    @Transactional
    public void delivery(Long orderId) {
        Order order = orderIssuing.searchOrFail(orderId);
        order.deliver();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderIssuing.searchOrFail(orderId);
        order.cancel();
    }
}
