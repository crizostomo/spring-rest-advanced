package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.PaymentModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Payments")
public interface ControllerRestaurantPaymentOpenApi {

    @ApiOperation(value = "It lists the payments associated to a restaurant")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public List<PaymentModel> list(@ApiParam(value = "Restaurant ID", example = "1", required = true)
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
