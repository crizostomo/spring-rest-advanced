package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.GroupModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Users")
public interface ControllerUserGroupOpenApi {

    @ApiOperation(value = "List the groups associated to a user")
    @ApiResponses({
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public CollectionModel<GroupModel> list(@ApiParam(value = "User Id", example = "1", required = true)
                                             Long userId);

    @ApiOperation(value = "Association of a group to a user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association done successfully"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public ResponseEntity association(@ApiParam(value = "User Id", example = "1", required = true)
                                    Long userId,
                                      @ApiParam(value = "Group Id", example = "1", required = true)
                                    Long groupId);

    @ApiOperation(value = "Association removal of a group to a user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association removal done successfully"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public ResponseEntity removeAssociation(@ApiParam(value = "User Id", example = "1", required = true)
                                    Long userId,
                                            @ApiParam(value = "Group Id", example = "1", required = true)
                                    Long groupId);
}
