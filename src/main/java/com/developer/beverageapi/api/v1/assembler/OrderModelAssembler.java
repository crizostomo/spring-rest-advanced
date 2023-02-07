package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerOrder;
import com.developer.beverageapi.api.v1.model.OrderModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public OrderModelAssembler() {
        super(ControllerOrder.class, OrderModel.class);
    }

    public OrderModel toModel(Order order) {
        OrderModel orderModel = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderModel);

        orderModel.add(instantiateLinks.linkToOrders("orders"));

        if (security.allowedToManageOrders(order.getCode())) {
            if (order.canBeConfirmed()) {
                orderModel.add(instantiateLinks.linkToOrderConfirmation(order.getCode(), "confirm"));
            }

            if (order.canBeCancelled()) {
                orderModel.add(instantiateLinks.linkToOrderCancellation(order.getCode(), "cancel"));
            }

            if (order.canBeDelivered()) {
                orderModel.add(instantiateLinks.linkToOrderDelivery(order.getCode(), "delivery"));
            }
        }

        orderModel.getRestaurant().add(instantiateLinks.linkToRestaurant(order.getRestaurant().getId()));

        orderModel.getClient().add(instantiateLinks.linkToUser(order.getClient().getId()));

        orderModel.getPayment().add(instantiateLinks.linkToPayment(order.getPayment().getId()));

//        orderModel.getDeliveryAddress().getCity().add(instantiateLinks.linkToCity(order.getAddress().getCity().getId()));
        // when calling the endpoint orders/{codeOrder}

        orderModel.getItems().forEach(item -> {
            item.add(instantiateLinks.linkToProduct(orderModel.getRestaurant().getId(), item.getId(), "product"));
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
