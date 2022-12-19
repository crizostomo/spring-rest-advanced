package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.GroupModel;
import com.developer.beverageapi.api.model.UserModel;
import com.developer.beverageapi.api.model.input.UserPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithoutPasswordInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Users")
public interface ControllerUserGroupOpenApi {

    @ApiOperation(value = "List the groups associated to a user")
    @ApiResponses({
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public List<GroupModel> list(@ApiParam(value = "User Id", example = "1", required = true)
                                             Long userId);

    @ApiOperation(value = "Association of a group to a user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association done successfully"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public void association(@ApiParam(value = "User Id", example = "1", required = true)
                                    Long userId,
                            @ApiParam(value = "Group Id", example = "1", required = true)
                                    Long groupId);

    @ApiOperation(value = "Association removal of a group to a user")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Association removal done successfully"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public void removeAssociation(@ApiParam(value = "User Id", example = "1", required = true)
                                    Long userId,
                            @ApiParam(value = "Group Id", example = "1", required = true)
                                    Long groupId);
}