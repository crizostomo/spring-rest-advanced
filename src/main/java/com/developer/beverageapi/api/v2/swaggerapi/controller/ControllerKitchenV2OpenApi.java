package com.developer.beverageapi.api.v2.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v2.model.KitchenModelV2;
import com.developer.beverageapi.api.v2.model.input.KitchenInputV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Kitchens")
public interface ControllerKitchenV2OpenApi {

    @ApiOperation(value = "List the Kitchens with pagination")
    public PagedModel<KitchenModelV2> list(Pageable pageable);

    @ApiOperation(value = "Search a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Kitchen id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Kitchen not found", response = APIError.class)
    })
    public KitchenModelV2 search(@ApiParam(value = "Kitchen Id", example = "1", required = true)
                                         Long kitchenId);

    @ApiOperation(value = "It records a kitchen")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Kitchen created")
    })
    public KitchenModelV2 add(@ApiParam(name = "body", value = "Kitchen Representation", required = true)
                                      KitchenInputV2 kitchenInput);

    @ApiOperation(value = "It updates a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Kitchen updated"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = APIError.class)
    })
    public KitchenModelV2 update(@ApiParam(value = "Kitchen Id", example = "1", required = true) Long kitchenId,
                                 @ApiParam(name = "body", value = "Kitchen Representation with new data")
                                         KitchenInputV2 kitchenInput);


    @ApiOperation(value = "It deletes a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Kitchen deleted"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "Kitchen Id", example = "1", required = true)
                               Long kitchenId);
}
