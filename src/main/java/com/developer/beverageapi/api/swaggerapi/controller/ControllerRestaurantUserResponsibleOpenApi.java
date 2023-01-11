package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.UserModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurants")
public interface ControllerRestaurantUserResponsibleOpenApi {

    @ApiOperation(value = "It lists the users associated to a restaurant")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurant not found", response = APIError.class)
    })
    public CollectionModel<UserModel> list(@ApiParam(value = "Restaurant ID", example = "1", required = true)
                                        Long restaurantId);

    @ApiOperation(value = "Restaurant Association with the user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association done successfully"),
            @ApiResponse(code = 404, message = "Restaurant or user not found", response = APIError.class)
    })
    public ResponseEntity association(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                    Long restaurantId,
                                      @ApiParam(value = "User Id", example = "1", required = true)
                                    Long userId);

    @ApiOperation(value = "Removal of Restaurant Association with User")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association Removal done successfully"),
            @ApiResponse(code = 404, message = "Restaurant or user not found", response = APIError.class)
    })
    public ResponseEntity removeAssociation(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                          Long restaurantId,
                                            @ApiParam(value = "User Id", example = "1", required = true)
                                          Long userId);
}
