package com.developer.beverageapi.api.v2.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v2.model.CityModelV2;
import com.developer.beverageapi.api.v2.model.input.CityInputV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cities")
public interface ControllerCityV2OpenApi {

    @ApiOperation(value = "List cities")
    public CollectionModel<CityModelV2> list();

    @ApiOperation(value = "Search a city by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "City id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public CityModelV2 search(@ApiParam(value = "City Id", example = "1", required = true)
                                      Long cityId);

    @ApiOperation(value = "It records a city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    public CityModelV2 add(@ApiParam(name = "body", value = "City Representation", required = true)
                                   CityInputV2 cityInput);

    @ApiOperation(value = "It updates a city by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public CityModelV2 update(@ApiParam(value = "City Id", example = "1", required = true) Long cityId,
                            @ApiParam(name = "body", value = "City Representation with new data")
                                    CityInputV2 cityInput);


    @ApiOperation(value = "It deletes a city by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City deleted"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "City Id", example = "1", required = true)
                               Long cityId);
}
