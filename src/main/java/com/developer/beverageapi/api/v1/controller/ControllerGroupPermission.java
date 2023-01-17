package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.assembler.PermissionModelAssembler;
import com.developer.beverageapi.api.v1.model.PermissionModel;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerGroupPermissionsOpenApi;
import com.developer.beverageapi.domain.model.Group;
import com.developer.beverageapi.domain.service.GroupRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerGroupPermission implements ControllerGroupPermissionsOpenApi {

    @Autowired
    private GroupRegistrationService registrationGroup;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @GetMapping
    public CollectionModel<PermissionModel> list(@PathVariable Long groupId) {
        Group group = registrationGroup.searchOrFail(groupId);

        CollectionModel<PermissionModel> permissionModels = permissionModelAssembler.toCollectionModel(group.getPermissions())
                .removeLinks()
                .add(instantiateLinks.linkToPermissionsGroup(groupId))
                .add(instantiateLinks.linkToPermissionsGroupAssociation(groupId, "association"));

        permissionModels.getContent().forEach(permissionModel -> {
            permissionModel.add(instantiateLinks.linkToPermissionsGroupRemoveAssociation(
                    groupId, permissionModel.getId(), "removeAssociation"));
        });

        return permissionModels;
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity association(@PathVariable Long groupId, @PathVariable Long permissionId) {
        registrationGroup.addPermission(groupId, permissionId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity removeAssociation(@PathVariable Long groupId, @PathVariable Long permissionId) {
        registrationGroup.removePermission(groupId, permissionId);

        return ResponseEntity.noContent().build();
    }
}
