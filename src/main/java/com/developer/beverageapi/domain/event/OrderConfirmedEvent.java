package com.developer.beverageapi.domain.event;

import com.developer.beverageapi.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {

    private Order order;
}
