package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.UserModel;
import com.developer.beverageapi.api.model.input.CityInput;
import com.developer.beverageapi.api.model.input.UserPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithoutPasswordInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Users")
public interface ControllerUserOpenApi {

    @ApiOperation(value = "List the users")
    public List<UserModel> list();

    @ApiOperation(value = "Search a user by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "User id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public UserModel search(@ApiParam(value = "User Id", example = "1", required = true)
                                    Long userId);

    @ApiOperation(value = "It records a user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created")
    })
    public UserModel add(@ApiParam(name = "body", value = "User Representation", required = true)
                                 UserWithPasswordInput userWithPasswordInput);

    @ApiOperation(value = "It updates a user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User updated"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public UserModel update(@ApiParam(value = "User Id", example = "1", required = true) Long userId,
                            @ApiParam(name = "body", value = "User Representation with new data")
                                    UserWithoutPasswordInput userWithoutPasswordInput);

    @ApiOperation(value = "It updates a user password")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User password updated successfully"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public void updatePassword(@ApiParam(value = "User Id", example = "1", required = true) Long userId,
                            @ApiParam(name = "body", value = "User Representation with new data")
                                    UserPasswordInput password);

    @ApiOperation(value = "It deletes a user by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User deleted"),
            @ApiResponse(code = 404, message = "User not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "User Id", example = "1", required = true)
                               Long userId);
}
