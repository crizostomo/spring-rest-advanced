package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.PermissionModelAssembler;
import com.developer.beverageapi.api.model.PermissionModel;
import com.developer.beverageapi.api.swaggerapi.controller.ControllerGroupPermissionsOpenApi;
import com.developer.beverageapi.domain.model.Group;
import com.developer.beverageapi.domain.service.GroupRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerGroupPermissions implements ControllerGroupPermissionsOpenApi {

    @Autowired
    private GroupRegistrationService registrationGroup;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @GetMapping
    public List<PermissionModel> list(@PathVariable Long groupId) {
        Group group = registrationGroup.searchOrFail(groupId);

        return permissionModelAssembler.toCollectionModel(group.getPermissions());
    }

    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void association(@PathVariable Long groupId, @PathVariable Long permissionId) {
        registrationGroup.addPermission(groupId, permissionId);
    }

    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssociation(@PathVariable Long groupId, @PathVariable Long permissionId) {
        registrationGroup.removePermission(groupId, permissionId);
    }
}
