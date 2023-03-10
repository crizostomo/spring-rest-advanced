package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.ProductModel;
import com.developer.beverageapi.api.v1.model.ProductPhotoModel;
import com.developer.beverageapi.api.v1.model.input.ProductInput;
import com.developer.beverageapi.api.v1.model.input.ProductPhotoInput;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Products", description = "It runs products")
@Api(tags = "Products")
public interface ControllerRestaurantProductOpenApi {

    @ApiOperation(value = "It lists the products of a restaurant")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Restaurant ID invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public CollectionModel<ProductModel> list(@ApiParam(value = "Restaurant ID", example = "1", required = true)
                                           Long restaurantId,
                                              @ApiParam(value = "It shows if it must or not include products not active in the listing result",
                                   example = "false", defaultValue = "false") Boolean includeNotActives);

//    @ApiOperation(value = "It lists the products of a restaurant")
//    @ApiResponses({
//            @ApiResponse(code = 400, message = "Restaurant or Product ID invalid", response = APIError.class),
//            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
//    })
//    public List<ProductModel> listActiveAndOrInactive(@ApiParam(value = "Restaurant ID", example = "1", required = true)
//                                                              Long restaurantId,
//                                                      @ApiParam(value = "It shows if it must be or not included products not active" +
//                                                              "in the list", example = "1", required = true)
//                                                              boolean includeInactive);

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

    @ApiOperation(value = "It deletes a product by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product deleted"),
            @ApiResponse(code = 404, message = "Product not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "Product Id", example = "1", required = true)
                               Long productId);


    @ApiOperation(value = "It searches the photo of a restaurant product",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Restaurant or Product ID invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Product photo not found", response = APIError.class)
    })
    public ProductPhotoModel searchPhoto(@ApiParam(value = "Restaurant ID", example = "1", required = true)
                                                 Long restaurantId,
                                         @ApiParam(value = "Product ID", example = "1", required = true)
                                                 Long productId);

    @ApiOperation(value = "It searches the photo of a restaurant product", hidden = true)
    ResponseEntity<?> getFile(Long restaurantId, Long productId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation(value = "It updates the product photo of a Restaurant")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product photo updated successfully"),
            @ApiResponse(code = 404, message = "Restaurant product not found", response = APIError.class)
    })
    public ProductPhotoModel updatePhoto(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                                 Long restaurantId,
                                         @ApiParam(value = "Product Id", example = "1", required = true)
                                                 Long productId,
                                         ProductPhotoInput productPhotoInput,
                                         @ApiParam(value = "File of the product photo (max 500KB, only JPG and PNG", required = true)
                                                     MultipartFile file) throws IOException;

    @ApiOperation(value = "It deletes a product photo of a restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product photo deleted"),
            @ApiResponse(code = 400, message = "Restaurant or Product ID invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Product photo not found", response = APIError.class)
    })
    public void deletePhoto(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                    Long restaurantId,
                            @ApiParam(value = "Product Id", example = "1", required = true)
                                    Long productId);
}
