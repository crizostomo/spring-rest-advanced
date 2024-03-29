package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.PaymentModel;
import com.developer.beverageapi.api.v1.swaggerapi.model.PaymentsModelOpenApi;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Payments", description = "It runs payments")
//@Api(tags = "Payments")
public interface ControllerRestaurantPaymentOpenApi {

    @ApiOperation(value = "It lists the payments associated to a restaurant", response = PaymentsModelOpenApi.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public CollectionModel<PaymentModel> list(@ApiParam(value = "Restaurant ID", example = "1", required = true)
                                                      Long restaurantId);

    @ApiOperation(value = "Restaurant Association with Payment")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association done successfully"),
            @ApiResponse(code = 404, message = "Restaurant or payment not found", response = APIError.class)
    })
    public void association(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                    Long restaurantId);

    @ApiOperation(value = "Removal of Restaurant Association with Payment")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association Removal done successfully"),
            @ApiResponse(code = 404, message = "Restaurant or payment not found", response = APIError.class)
    })
    public void removeAssociation(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                          Long restaurantId);
}
