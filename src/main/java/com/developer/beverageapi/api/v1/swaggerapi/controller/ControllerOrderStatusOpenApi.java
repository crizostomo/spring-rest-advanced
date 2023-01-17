package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import io.swagger.annotations.*;

@Api(tags = "Orders")
public interface ControllerOrderStatusOpenApi {

    @ApiOperation(value = "Order Confirmation")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order confirmed successfully"),
            @ApiResponse(code = 404, message = "Order not found", response = APIError.class)
    })
    public void confirm(@ApiParam(value = "Order code", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                       String codeOrder);

    @ApiOperation(value = "Order Cancellation")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order cancelled successfully"),
            @ApiResponse(code = 404, message = "Order not found", response = APIError.class)
    })
    public void cancel(@ApiParam(value = "Order code", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                       String codeOrder);

    @ApiOperation(value = "Order Delivery")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order delivered successfully"),
            @ApiResponse(code = 404, message = "Order not found", response = APIError.class)
    })
    public void delivery(@ApiParam(value = "Order code", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true)
                       String codeOrder);
}
