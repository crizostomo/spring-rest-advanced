package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.OrderModelAssembler;
import com.developer.beverageapi.api.assembler.OrderSummaryModelAssembler;
import com.developer.beverageapi.api.model.OrderModel;
import com.developer.beverageapi.api.model.OrderSummaryModel;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import com.developer.beverageapi.domain.service.OrderIssuingRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class ControllerOrder {

    @Autowired
    private OrderIssuingRegistrationService issuingOrder;

    @Autowired
    private RepositoryOrder repositoryOrder;

    @Autowired
    private OrderModelAssembler orderModelAssembler;

    @Autowired
    private OrderSummaryModelAssembler orderSummaryModelAssembler;

    @GetMapping
    public List<OrderModel> list() {
        List<Order> allOrders = repositoryOrder.findAll();

        return orderModelAssembler.toCollectionModel(allOrders);
    }

    @GetMapping("/summary")
    public List<OrderSummaryModel> listSummary() {
        List<Order> allOrders = repositoryOrder.findAll();

        return orderSummaryModelAssembler.toCollectionModel(allOrders);
    }

    @GetMapping("/{orderId}")
    public OrderModel search(@PathVariable Long orderId) {
        Order order = issuingOrder.searchOrFail(orderId);

        return orderModelAssembler.toModel(order);
    }
}
