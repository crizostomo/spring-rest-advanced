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
import com.developer.beverageapi.domain.repository.filter.OrderFilter;
import com.developer.beverageapi.domain.service.OrderIssuingRegistrationService;
import com.developer.beverageapi.infrastructure.repository.spec.OrderSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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

    @GetMapping("/filter")
    public List<OrderModel> search(OrderFilter filter) {
        List<Order> allOrders = repositoryOrder.findAll(OrderSpecs.usingFilter(filter));

        return orderModelAssembler.toCollectionModel(allOrders);
    }

    @GetMapping("/summary")
    public List<OrderSummaryModel> listSummary() {
        List<Order> allOrders = repositoryOrder.findAll();

        return orderSummaryModelAssembler.toCollectionModel(allOrders);
    }

//    @GetMapping("/summary-customized")
//    public MappingJacksonValue listSummaryJackson(@RequestParam(required = false) String fields) {
//        List<Order> allOrders = repositoryOrder.findAll();
//        List<OrderSummaryModel> ordersModel = orderSummaryModelAssembler.toCollectionModel(allOrders);
//
//        MappingJacksonValue ordersWrapper = new MappingJacksonValue(ordersModel); // It allows customizing what you want
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(fields)) {
//            filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//        }
//
//        ordersWrapper.setFilters(filterProvider);
//
//        return ordersWrapper;
//    }

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
