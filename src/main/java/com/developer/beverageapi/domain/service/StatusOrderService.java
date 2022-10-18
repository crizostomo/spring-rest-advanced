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
    public void confirm (Long orderId) {
        Order order = orderIssuing.searchOrFail(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(
                    String.format("Order status %d cannot be altered from %s to %s",
                            order.getId(), order.getStatus().getDescription(), OrderStatus.CONFIRMED.getDescription()));
        }
        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void delivery (Long orderId) {
        Order order = orderIssuing.searchOrFail(orderId);

        if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
            throw new BusinessException(
                    String.format("Order status %d cannot be altered from %s to %s",
                            order.getId(), order.getStatus().getDescription(), OrderStatus.DELIVERED.getDescription()));
        }
        order.setStatus(OrderStatus.DELIVERED);
        order.setConfirmationDate(OffsetDateTime.now());
    }

    @Transactional
    public void cancel (Long orderId) {
        Order order = orderIssuing.searchOrFail(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(
                    String.format("Order status %d cannot be altered from %s to %s",
                            order.getId(), order.getStatus().getDescription(), OrderStatus.CANCELLED.getDescription()));
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setConfirmationDate(OffsetDateTime.now());
    }
}
