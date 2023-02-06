package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.OrderInputDismantle;
import com.developer.beverageapi.api.v1.assembler.OrderModelAssembler;
import com.developer.beverageapi.api.v1.assembler.OrderSummaryModelAssembler;
import com.developer.beverageapi.api.v1.model.OrderModel;
import com.developer.beverageapi.api.v1.model.OrderSummaryModel;
import com.developer.beverageapi.api.v1.model.input.OrderInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerOrderOpenApi;
import com.developer.beverageapi.core.data.PageWrapper;
import com.developer.beverageapi.core.data.PageableTranslator;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.repository.RepositoryOrder;
import com.developer.beverageapi.domain.filter.OrderFilter;
import com.developer.beverageapi.domain.service.OrderIssuingRegistrationService;
import com.developer.beverageapi.infrastructure.repository.spec.OrderSpecs;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerOrder implements ControllerOrderOpenApi {

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

    @Autowired
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Autowired
    private Security security;

    @GetMapping
    public List<OrderModel> list() {
        List<Order> allOrders = repositoryOrder.findAll();

        return orderModelAssembler.toCollectionModelList(allOrders);
    }

    @Override
    @GetMapping("/filter")
    public PagedModel<OrderSummaryModel> search(OrderFilter filter, @PageableDefault(size = 10)Pageable pageable) {
        Pageable translatedPageable = translatePageable(pageable);

        Page<Order> ordersPage = repositoryOrder.findAll(OrderSpecs.usingFilter(filter), translatedPageable);

        ordersPage = new PageWrapper<>(ordersPage, pageable);

        return pagedResourcesAssembler.toModel(ordersPage, orderSummaryModelAssembler);
    }

//    @GetMapping("/filter")
//    public List<OrderModel> search(OrderFilter filter) {
//        List<Order> allOrders = repositoryOrder.findAll(OrderSpecs.usingFilter(filter));
//
//        return orderModelAssembler.toCollectionModel(allOrders);
//    }

    @GetMapping("/summary")
    public CollectionModel<OrderSummaryModel> listSummary() {
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
            newOrder.getClient().setId(security.getUserId());

            newOrder = issuingOrder.issue(newOrder);

            return orderModelAssembler.toModel(newOrder);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Orders.AllowedToSearch
    @GetMapping(value = "/{codeOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderModel search(@PathVariable String codeOrder) {
        Order order = issuingOrder.searchOrFail(codeOrder);

        return orderModelAssembler.toModel(order);
    }

    private Pageable translatePageable(Pageable apiPageable) {
        var mapping = ImmutableMap.of(
                "code", "code",
                "restaurante.name", "restaurante.name",
                "clienteName", "cliente.name",
                "total", "total"
        );

        return PageableTranslator.translate(apiPageable, mapping);
    }
}
