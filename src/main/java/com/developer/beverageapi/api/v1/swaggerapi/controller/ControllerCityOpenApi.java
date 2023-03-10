package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.CityModel;
import com.developer.beverageapi.api.v1.model.input.CityInput;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cities", description = "It runs cities")
//@Api(tags = "Cities")
public interface ControllerCityOpenApi {

    @Operation(summary = "List cities")
    public CollectionModel<CityModel> list();

    @Operation(summary = "Search a city by id", description = "The record of a city needs a state and a valid name")
    @ApiResponses({
            @ApiResponse(code = 400, message = "City id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public CityModel search(@Parameter(description = "City Id", example = "1", required = true)
                                        Long cityId);

    @Operation(summary = "It records a city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    public CityModel add(@RequestBody(description = "City Representation", required = true)
                         CityInput cityInput);

    @Operation(summary = "It updates a city by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public CityModel update(@Parameter(description = "City Id", example = "1", required = true) Long cityId,
                            @RequestBody(description = "City Representation with new data", required = true)
                            CityInput cityInput);


    @Operation(summary = "It deletes a city by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City deleted"),
            @ApiResponse(code = 404, message = "City not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "City Id", example = "1", required = true)
                       Long cityId);
}
