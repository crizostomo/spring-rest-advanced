package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.PermissionModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Groups")
public interface ControllerGroupPermissionsOpenApi {

    @ApiOperation(value = "List the permissions associated to a group")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Group Id id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Group not found", response = APIError.class)
    })
    public CollectionModel<PermissionModel> list(Long groupId);

    @ApiOperation(value = "Association of permission to a group")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association done successfully", response = APIError.class),
            @ApiResponse(code = 404, message = "Group or permission not found", response = APIError.class)
    })
    public ResponseEntity association(@ApiParam(value = "Group Id", example = "1", required = true)
                                    Long groupId,
                                      @ApiParam(value = "Group Id", example = "1", required = true)
                                    Long PermissionId);


    @ApiOperation(value = "Association removal of permission to a group")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association removal done successfully", response = APIError.class),
            @ApiResponse(code = 404, message = "Group or permission not found", response = APIError.class)
    })
    public ResponseEntity removeAssociation(@ApiParam(value = "Group Id", example = "1", required = true)
                                    Long groupId,
                                            @ApiParam(value = "Group Id", example = "1", required = true)
                                    Long PermissionId);
}
