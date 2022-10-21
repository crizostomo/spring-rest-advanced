package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.OrderInputDismantle;
import com.developer.beverageapi.api.assembler.OrderModelAssembler;
import com.developer.beverageapi.api.assembler.OrderSummaryModelAssembler;
import com.developer.beverageapi.api.model.OrderModel;
import com.developer.beverageapi.api.model.OrderSummaryModel;
import com.developer.beverageapi.api.model.input.OrderInput;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import com.developer.beverageapi.domain.service.OrderIssuingRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private OrderInputDismantle orderInputDismantle;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel add(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = orderInputDismantle.toDomainObject(orderInput);

            // TO DO pick user authenticated
            newOrder.setClient(new User());
            newOrder.getClient().setId(1L);

            newOrder = issuingOrder.issue(newOrder);

            return orderModelAssembler.toModel(newOrder);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @GetMapping("/{codeOrder}")
    public OrderModel search(@PathVariable String codeOrder) {
        Order order = issuingOrder.searchOrFail(codeOrder);

        return orderModelAssembler.toModel(order);
    }
}
