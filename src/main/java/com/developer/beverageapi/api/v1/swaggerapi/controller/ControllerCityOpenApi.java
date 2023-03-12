package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.v1.model.CityModel;
import com.developer.beverageapi.api.v1.model.input.CityInput;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cities", description = "It runs cities")
public interface ControllerCityOpenApi {

    @Operation(summary = "List cities")
    public CollectionModel<CityModel> list();

    @Operation(summary = "Search a city by id", description = "The record of a city needs a state and a valid name", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "City id invalid",
                    content = @Content(schema = @Schema(ref = "APIError"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "City not found")
    })
    public CityModel search(@Parameter(description = "City Id", example = "1", required = true)
                                    Long cityId);

    @Operation(summary = "It records a city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    public CityModel add(@RequestBody(description = "City Representation", required = true)
                                 CityInput cityInput);

    @Operation(summary = "It updates a city by id", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "City updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(ref = "APIError")))
    })
    public CityModel update(@Parameter(description = "City Id", example = "1", required = true) Long cityId,
                            @RequestBody(description = "City Representation with new data", required = true)
                                    CityInput cityInput);

    @Operation(summary = "It deletes a city by id", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "City deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(ref = "APIError")))
    })
    public void delete(@ApiParam(value = "City Id", example = "1", required = true)
                               Long cityId);
}
