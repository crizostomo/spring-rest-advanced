package com.developer.beverageapi.api.v1.swaggerapi.controller;

import com.developer.beverageapi.api.v1.model.PermissionModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Permissions", description = "It runs permissions")
//@Api(tags = "Permissions")
public interface ControllerPermissionOpenApi {

    @ApiOperation(value = "It lists permissions")
    public CollectionModel<PermissionModel> list();
}
