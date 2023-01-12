package com.developer.beverageapi.api.swaggerapi.controller;

import com.developer.beverageapi.api.model.PermissionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissions")
public interface ControllerPermissionOpenApi {

    @ApiOperation(value = "It lists permissions")
    public CollectionModel<PermissionModel> list();
}
