package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.OrderSummaryModel;
import com.developer.beverageapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderSummaryModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public OrderSummaryModel toModel(Order order) {

        return modelMapper.map(order, OrderSummaryModel.class);
    }

    public List<OrderSummaryModel> toCollectionModel(List<Order> orders) {
        return orders.stream()
                .map(order -> toModel(order))
                .collect(Collectors.toList());
    }
}
