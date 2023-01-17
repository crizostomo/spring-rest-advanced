package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.KitchenModel;
import com.developer.beverageapi.api.v1.model.input.KitchenInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Kitchens")
public interface ControllerKitchenOpenApi {

    @ApiOperation(value = "List the Kitchens with pagination")
    public PagedModel<KitchenModel> list(Pageable pageable);

    @ApiOperation(value = "Search a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Kitchen id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Kitchen not found", response = APIError.class)
    })
    public KitchenModel search(@ApiParam(value = "Kitchen Id", example = "1", required = true)
                                        Long kitchenId);

    @ApiOperation(value = "It records a kitchen")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Kitchen created")
    })
    public KitchenModel add(@ApiParam(name = "body", value = "Kitchen Representation", required = true)
                                    KitchenInput kitchenInput);

    @ApiOperation(value = "It updates a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Kitchen updated"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = APIError.class)
    })
    public KitchenModel update(@ApiParam(value = "Kitchen Id", example = "1", required = true) Long kitchenId,
                            @ApiParam(name = "body", value = "Kitchen Representation with new data")
                                    KitchenInput kitchenInput);


    @ApiOperation(value = "It deletes a kitchen by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Kitchen deleted"),
            @ApiResponse(code = 404, message = "Kitchen not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "Kitchen Id", example = "1", required = true)
                       Long kitchenId);
}
