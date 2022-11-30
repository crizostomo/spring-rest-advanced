package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.input.CityInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Cities")
public interface ControllerCityOpenApi {

    @ApiOperation(value = "List cities")
    public List<CityModel> list();

    @ApiOperation(value = "Search a city by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "City id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public CityModel search(@ApiParam(value = "City Id", example = "1", required = true)
                                        Long cityId);

    @ApiOperation(value = "It records a city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    public CityModel add(@ApiParam(name = "body", value = "City Representation", required = true)
                         CityInput cityInput);

    @ApiOperation(value = "It updates a city by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public CityModel update(@ApiParam(value = "City Id", example = "1", required = true) Long cityId,
                            @ApiParam(name = "body", value = "City Representation with new data")
                            CityInput cityInput);


    @ApiOperation(value = "It deletes a city by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City deleted"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "City Id", example = "1", required = true)
                       Long cityId);
}
