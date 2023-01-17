package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.model.input.OrderInput;
import com.developer.beverageapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public Order toDomainObject(OrderInput orderInput) {

        return modelMapper.map(orderInput, Order.class);
    }

    public void copyToDomainObject(OrderInput orderInput, Order order) {
        modelMapper.map(orderInput, order);
    }
}
