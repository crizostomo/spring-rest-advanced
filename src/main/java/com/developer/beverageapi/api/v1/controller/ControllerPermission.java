package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.PermissionModelAssembler;
import com.developer.beverageapi.api.v1.model.PermissionModel;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerPermissionOpenApi;
import com.developer.beverageapi.domain.model.Permission;
import com.developer.beverageapi.domain.repository.RepositoryPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerPermission implements ControllerPermissionOpenApi {

    @Autowired
    private RepositoryPermission repositoryPermission;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @GetMapping
    public CollectionModel<PermissionModel> list() {
        List<Permission> allPermissions = repositoryPermission.findAll();

        return permissionModelAssembler.toCollectionModel(allPermissions);
    }
}
