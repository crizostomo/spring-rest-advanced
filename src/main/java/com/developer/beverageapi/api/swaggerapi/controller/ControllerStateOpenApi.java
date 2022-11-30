package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.exceptionHandler.APIError;
import com.developer.beverageapi.api.model.StateModel;
import com.developer.beverageapi.api.model.input.StateInput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "States")
public interface ControllerStateOpenApi {

    @ApiOperation(value = "List states")
    public List<StateModel> list();

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
