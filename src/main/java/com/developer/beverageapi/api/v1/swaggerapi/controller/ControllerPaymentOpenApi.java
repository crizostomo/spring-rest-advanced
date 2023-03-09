package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.PaymentModel;
import com.developer.beverageapi.api.v1.model.input.PaymentInput;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
@Api(tags = "Payments")
public interface ControllerPaymentOpenApi {

    @ApiOperation(value = "List payments")
    public ResponseEntity<CollectionModel<PaymentModel>> list(ServletWebRequest request);

    @ApiOperation(value = "Search a payment by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Payment id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Payment not found", response = APIError.class)
    })
    public ResponseEntity<PaymentModel> search(@ApiParam(value = "Payment Id", example = "1", required = true)
                                        Long paymentId, ServletWebRequest request);

    @ApiOperation(value = "It records a payment")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Payment created")
    })
    public PaymentModel add(@ApiParam(name = "body", value = "Payment Representation", required = true)
                                        PaymentInput paymentInput);

    @ApiOperation(value = "It updates a payment by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Payment updated"),
            @ApiResponse(code = 404, message = "Payment not found", response = APIError.class)
    })
    public PaymentModel update(@ApiParam(value = "City Id", example = "1") Long paymentId,
                            @ApiParam(name = "body", value = "City Representation with new data", required = true)
                                    PaymentInput paymentInput);


    @ApiOperation(value = "It deletes a payment by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Payment deleted"),
            @ApiResponse(code = 404, message = "Payment not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "Payment Id", example = "1", required = true)
                       Long paymentId);
}
