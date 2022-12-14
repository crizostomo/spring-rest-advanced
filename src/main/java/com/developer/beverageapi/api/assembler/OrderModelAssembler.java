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

        orderModel.getRestaurant().add(instantiateLinks.linkToRestaurant(order.getRestaurant().getId()));

        orderModel.getClient().add(instantiateLinks.linkToUser(order.getClient().getId()));

        orderModel.getPayment().add(instantiateLinks.linkToPayment(order.getPayment().getId()));

        orderModel.getDeliveryAddress().getCity().add(instantiateLinks.linkToCity(order.getAddress().getCity().getId()));

        orderModel.getItems().forEach(item -> {
            item.add(instantiateLinks.linkToProduct(orderModel.getRestaurant().getId(), item.getId(),"product"));
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
