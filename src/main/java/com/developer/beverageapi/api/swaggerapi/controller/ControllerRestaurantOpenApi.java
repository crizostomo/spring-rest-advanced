package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.RestaurantModel;
import com.developer.beverageapi.api.model.input.RestaurantInput;
import com.developer.beverageapi.api.model.input.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Restaurants")
public interface ControllerRestaurantOpenApi {

    @ApiOperation(value = "List Restaurants")
    public List<RestaurantModel> list();

    @ApiOperation(value = "List Restaurants Summarized")
    @JsonView(RestaurantView.Summary.class)
    public List<RestaurantModel> listSummary();

    @ApiOperation(value = "Search a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Restaurant id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public RestaurantModel search(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                        Long restaurantId);

    @ApiOperation(value = "It records a restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurant created")
    })
    public RestaurantModel add(@ApiParam(name = "body", value = "Restaurant Representation", required = true)
                                       RestaurantInput restaurantInput);

    @ApiOperation(value = "It updates a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurant updated"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public RestaurantModel update(@ApiParam(value = "Restaurant Id", example = "1", required = true) Long restaurantId,
                            @ApiParam(name = "body", value = "Restaurant Representation with new data")
                                    RestaurantInput restaurantInput);

    @ApiOperation(value = "It actives a restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant activated"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public void active(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                       Long restaurantId);

    @ApiOperation(value = "It deactivates a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant deactivated"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public void inactive(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                       Long restaurantId);

    @ApiOperation(value = "It activates multiples restaurants")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurants activated successfully")})
    public void activateMultiples(@ApiParam(name = "Body", value = "Restaurants IDs", required = true)
                                          List<Long> restaurantId);

    @ApiOperation(value = "It deactivates multiples restaurants")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurants deactivated successfully")})
    public void deactivateMultiples(@ApiParam(name = "Body", value = "Restaurants IDs", required = true)
                                          List<Long> restaurantId);

    @ApiOperation(value = "It opens a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant opened successfully"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public void open(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                 Long restaurantId);

    @ApiOperation(value = "It closes a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant closed successfully"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public void close(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                             Long restaurantId);

    @ApiOperation(value = "It deletes a restaurant by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant deleted"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                       Long restaurantId);
}
