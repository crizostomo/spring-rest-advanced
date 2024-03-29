package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.GroupInputDismantle;
import com.developer.beverageapi.api.v1.assembler.GroupModelAssembler;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerGroupOpenApi;
import com.developer.beverageapi.api.v1.model.GroupModel;
import com.developer.beverageapi.api.v1.model.input.GroupInput;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.domain.model.Group;
import com.developer.beverageapi.domain.repository.RepositoryGroup;
import com.developer.beverageapi.domain.service.GroupRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerGroup implements ControllerGroupOpenApi {

    @Autowired
    private RepositoryGroup repositoryGroup;

    @Autowired
    private GroupRegistrationService registrationGroup;

    @Autowired
    private GroupModelAssembler groupModelAssembler;

    @Autowired
    private GroupInputDismantle groupInputDismantle;

    @Override
    @CheckSecurity.UsersGroupsPermissions.AllowedToConsult
    @GetMapping
    public CollectionModel<GroupModel> list() {
        List<Group> allGroups = repositoryGroup.findAll();

        return groupModelAssembler.toCollectionModel(allGroups);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToConsult
    @GetMapping("/{groupId}")
    public GroupModel search(@PathVariable Long groupId) {
        Group group = registrationGroup.searchOrFail(groupId);

        return groupModelAssembler.toModel(group);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupModel add(@RequestBody @Valid GroupInput groupInput) {
        Group group = groupInputDismantle.toDomainObject(groupInput);

        group = registrationGroup.add(group);

        return groupModelAssembler.toModel(group);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToEdit
    @PutMapping("/{groupId}")
    public GroupModel update(@PathVariable Long groupId,
                             @RequestBody @Valid GroupInput groupInput) {
        Group currentGroup = registrationGroup.searchOrFail(groupId);

        groupInputDismantle.copyToDomainObject(groupInput, currentGroup);

        currentGroup = registrationGroup.add(currentGroup);

        return groupModelAssembler.toModel(currentGroup);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToEdit
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long groupId) {
        registrationGroup.remove(groupId);
    }
}
