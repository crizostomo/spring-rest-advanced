package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.controller.ControllerOrder;
import com.developer.beverageapi.api.controller.ControllerRestaurant;
import com.developer.beverageapi.api.controller.ControllerUser;
import com.developer.beverageapi.api.model.OrderSummaryModel;
import com.developer.beverageapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderSummaryModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderSummaryModel> {

    @Autowired
    private ModelMapper modelMapper;

    public OrderSummaryModelAssembler() {
        super(ControllerOrder.class, OrderSummaryModel.class);
    }

    @Override
    public OrderSummaryModel toModel(Order order) {
        OrderSummaryModel orderSummaryModel = createModelWithId(order.getCode(), order);

        modelMapper.map(order, orderSummaryModel);

        orderSummaryModel.add(WebMvcLinkBuilder.linkTo(ControllerOrder.class).withRel("orders"));

        orderSummaryModel.getRestaurant().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class)
                .search(order.getRestaurant().getId())).withSelfRel());

        orderSummaryModel.getClient().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerUser.class)
                .search(order.getClient().getId())).withSelfRel());

        return orderSummaryModel;
    }
}
