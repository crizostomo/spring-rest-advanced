package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.OrderModel;
import com.developer.beverageapi.api.v1.model.OrderSummaryModel;
import com.developer.beverageapi.api.v1.model.input.OrderInput;
import com.developer.beverageapi.domain.filter.OrderFilter;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Api(tags = "Orders")
public interface ControllerOrderOpenApi {

    @ApiOperation(value = "List orders")
    public List<OrderModel> list();

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Properties name to filter in the response, separated by comma",
                    name = "fields", paramType = "query", type = "string")
    })
    @ApiOperation(value = "Search an order by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Order id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Order not found", response = APIError.class)
    })
    public PagedModel<OrderSummaryModel> search(OrderFilter filter, Pageable pageable);

    @ApiOperation(value = "Search an order by id summarized")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Order id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Order not found", response = APIError.class)
    })
    public CollectionModel<OrderSummaryModel> listSummary();

    @ApiOperation(value = "It records an order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order created")
    })
    public OrderModel add(@ApiParam(name = "body", value = "Order Representation")
                                  OrderInput orderInput);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Properties name to filter in the response, separated by comma",
                    name = "fields", paramType = "query", type = "string")
    })
    @ApiOperation(value = "Search a code order")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Order id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Order not found", response = APIError.class)
    })
    public OrderModel search(@ApiParam(value = "Order Id", example = "f8881ca4-5a6e-1de3-bf04-933865dg3e55", required = true)
                                         String codeOrder);
}

