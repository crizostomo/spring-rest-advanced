package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.v1.model.KitchenModel;
import com.developer.beverageapi.api.v1.model.input.KitchenInput;
import com.developer.beverageapi.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Kitchens", description = "It runs kitchens")
public interface ControllerKitchenOpenApi {

    @Operation(summary = "List the Kitchens with pagination")
    @PageableParameter
    public PagedModel<KitchenModel> list(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Search a kitchen by id", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "Kitchen id invalid",
                    content = @Content(schema = @Schema(ref = "APIError"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Kitchen not found")
    })
    public KitchenModel search(@Parameter(description = "Kitchen Id", example = "1", required = true)
                                        Long kitchenId);

    @Operation(summary = "It records a kitchen", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Kitchen created"),
    })
    public KitchenModel add(@RequestBody(description = "Kitchen Representation", required = true)
                                    KitchenInput kitchenInput);

    @Operation(summary = "It updates a kitchen by id", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Kitchen updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Kitchen not found")
    })
    public KitchenModel update(@Parameter(description = "Kitchen Id", example = "1", required = true) Long kitchenId,
                            @RequestBody(description = "Kitchen Representation with new data")
                                    KitchenInput kitchenInput);

    @Operation(summary = "It deletes a kitchen by id", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Kitchen deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Kitchen not found")
    })
    public void delete(@Parameter(description = "Kitchen Id", example = "1", required = true)
                       Long kitchenId);
}
