package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.*;
import com.developer.beverageapi.api.model.OrderModel;
import com.developer.beverageapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public OrderModelAssembler() {
        super(ControllerOrder.class, OrderModel.class);
    }

    public OrderModel toModel(Order order) {
        OrderModel orderModel = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderModel);

        orderModel.add(instantiateLinks.linkToOrders());

        orderModel.getRestaurant().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class)
                .search(order.getRestaurant().getId())).withSelfRel());

        orderModel.getClient().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerUser.class)
                .search(order.getClient().getId())).withSelfRel());

        orderModel.getPayment().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerPayment.class)
                .search(order.getPayment().getId(), null)).withSelfRel());

        orderModel.getDeliveryAddress().getCity().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerCity.class)
                .search(order.getAddress().getCity().getId())).withSelfRel());

        orderModel.getItems().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantProduct.class)
                    .search(orderModel.getRestaurant().getId(), item.getId())).withRel("product"));
        });

        return orderModel;
    }

    public OrderModel toModelList(Order order) {
        return modelMapper.map(order, OrderModel.class);
    }

    public List<OrderModel> toCollectionModelList(List<Order> orders) {
        return orders.stream()
                .map(order -> toModelList(order))
                .collect(Collectors.toList());
    }
}
