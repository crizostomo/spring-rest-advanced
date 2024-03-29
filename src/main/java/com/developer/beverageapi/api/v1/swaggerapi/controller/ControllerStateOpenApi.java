package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.v1.model.StateModel;
import com.developer.beverageapi.api.v1.model.input.StateInput;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "States", description = "It runs states")
//@Api(tags = "States")
public interface ControllerStateOpenApi {

    @ApiOperation(value = "List states")
    public CollectionModel<StateModel> list();

    @ApiOperation(value = "Search a state by id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "State id invalid", response = APIError.class),
            @ApiResponse(code = 404, message = "State not found", response = APIError.class)
    })
    public StateModel search(@ApiParam(value = "State Id", example = "1", required = true)
                                        Long stateId);

    @ApiOperation(value = "It records a state")
    @ApiResponses({
            @ApiResponse(code = 201, message = "State created")
    })
    public StateModel add(@ApiParam(name = "body", value = "State Representation", required = true)
                                  StateInput stateInput);

    @ApiOperation(value = "It updates a state by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "State updated"),
            @ApiResponse(code = 404, message = "State not found", response = APIError.class)
    })
    public StateModel update(@ApiParam(value = "State Id", example = "1", required = true) Long stateId,
                            @ApiParam(name = "body", value = "State Representation with new data")
                            StateInput stateInput);

    @ApiOperation(value = "It deletes a state by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "State deleted"),
            @ApiResponse(code = 404, message = "State not found", response = APIError.class)
    })
    public void delete(@ApiParam(value = "State Id", example = "1", required = true)
                       Long stateId);
}
