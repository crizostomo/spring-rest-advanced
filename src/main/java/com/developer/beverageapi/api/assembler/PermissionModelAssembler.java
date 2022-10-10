package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.PermissionModel;
import com.developer.beverageapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissionModel toModel(Permission permission) {

        return modelMapper.map(permission, PermissionModel.class);
    }

    public List<PermissionModel> toCollectionModel(List<Permission> permissions) {
        return permissions.stream()
                .map(permission -> toModel(permission))
                .collect(Collectors.toList());
    }
}
