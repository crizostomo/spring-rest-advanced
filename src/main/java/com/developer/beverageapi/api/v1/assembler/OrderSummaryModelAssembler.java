package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerOrder;
import com.developer.beverageapi.api.v1.model.OrderSummaryModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderSummaryModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderSummaryModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public OrderSummaryModelAssembler() {
        super(ControllerOrder.class, OrderSummaryModel.class);
    }

    @Override
    public OrderSummaryModel toModel(Order order) {
        OrderSummaryModel orderSummaryModel = createModelWithId(order.getCode(), order);

        modelMapper.map(order, orderSummaryModel);

        if (security.allowedToSearchOrders()) {
            orderSummaryModel.add(instantiateLinks.linkToOrders("orders"));
        }

        if (security.allowedToConsultRestaurants()) {
            orderSummaryModel.getRestaurant().add(instantiateLinks.linkToRestaurant(order.getRestaurant().getId()));
        }

        if (security.allowedToConsultUsersGroupsPermissions()) {
            orderSummaryModel.getClient().add(instantiateLinks.linkToUser(order.getClient().getId()));
        }

        return orderSummaryModel;
    }
}
