package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.GroupModel;
import com.developer.beverageapi.api.v1.model.input.GroupInput;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Groups", description = "It runs the users groups")
//@Api(tags = "Groups")
public interface ControllerGroupOpenApi {

    @ApiOperation(value = "List Groups")
    public CollectionModel<GroupModel> list();

    @ApiOperation(value = "Search a group by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Group id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "Group not found", response = APIError.class)
    })
    public GroupModel search(@ApiParam(value = "Group Id", example = "1", required = true)
                                        Long groupId);

    @ApiOperation(value = "It records a group")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Group created")
    })
    public GroupModel add(@ApiParam(name = "body", value = "Group Representation", required = true)
                                  GroupInput groupInput);

    @ApiOperation(value = "It updates a group by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Group updated"),
            @ApiResponse(code = 404, message = "Group not found", response = APIError.class)
    })
    public GroupModel update(@ApiParam(value = "Group Id", example = "1") Long groupId,
                            @ApiParam(name = "body", value = "Group Representation with new data", required = true)
                                    GroupInput groupInput);


    @ApiOperation(value = "It deletes a group by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Group deleted"),
            @ApiResponse(code = 404, message = "Group not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "Group Id", example = "1", required = true)
                       Long groupId);
}
