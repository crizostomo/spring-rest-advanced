package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.model.PermissionModel;
import com.developer.beverageapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionModelAssembler implements RepresentationModelAssembler<Permission, PermissionModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public PermissionModel toModel(Permission permission) {
        PermissionModel permissionModel = modelMapper.map(permission, PermissionModel.class);

        return permissionModel;
    }

    @Override
    public CollectionModel<PermissionModel> toCollectionModel(Iterable<? extends Permission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(instantiateLinks.linkToPermissions());
    }
}
