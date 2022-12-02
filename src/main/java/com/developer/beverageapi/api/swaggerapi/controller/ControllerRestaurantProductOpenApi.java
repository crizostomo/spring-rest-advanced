package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.api.model.ProductModel;
import com.developer.beverageapi.api.model.input.ProductInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Products")
public interface ControllerRestaurantProductOpenApi {

    @ApiOperation(value = "It lists the products of a restaurant")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Restaurant ID invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public List<ProductModel> list(@ApiParam(value = "Restaurant ID", example = "1", required = true)
                                           Long restaurantId);

    @ApiOperation(value = "It lists the products of a restaurant")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Restaurant or Product ID invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public List<ProductModel> listActiveAndOrInactive(@ApiParam(value = "Restaurant ID", example = "1", required = true)
                                                              Long restaurantId,
                                                      @ApiParam(value = "It shows if it must be or not included products not active" +
                                                              "in the list", example = "1", required = true)
                                                              boolean includeInactive);

    @ApiOperation(value = "It searches the products of a restaurant")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Restaurant or product ID invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public ProductModel search(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                       Long restaurantId,
                               @ApiParam(value = "Product Id", example = "1", required = true)
                                       Long productId);

    @ApiOperation(value = "It records the product of a Restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product recorded successfully"),
            @ApiResponse(code = 404, message = "Restaurant or payment not found", response = APIError.class)
    })
    public ProductModel add(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                    Long restaurantId,
                            @ApiParam(value = "body", example = "Representation of a new Product", required = true)
                                    ProductInput productInput);


    @ApiOperation(value = "It updates the product of a Restaurant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product updated successfully"),
            @ApiResponse(code = 404, message = "Restaurant or payment not found", response = APIError.class)
    })
    public ProductModel update(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                    Long restaurantId,
                               @ApiParam(value = "Product Id", example = "1", required = true)
                                       Long productId,
                            @ApiParam(value = "body", example = "Representation of a Product with new data", required = true)
                                    ProductInput productInput);
}
