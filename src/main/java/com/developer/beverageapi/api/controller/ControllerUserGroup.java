package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.assembler.GroupModelAssembler;
import com.developer.beverageapi.api.model.GroupModel;
import com.developer.beverageapi.api.swaggerapi.controller.ControllerUserGroupOpenApi;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerUserGroup implements ControllerUserGroupOpenApi {

    @Autowired
    private UserRegistrationService registrationUser;

    @Autowired
    private GroupModelAssembler groupModelAssembler;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Override
    @GetMapping
    public CollectionModel<GroupModel> list(@PathVariable Long userId) {
        User user = registrationUser.searchOrFail(userId);

        CollectionModel<GroupModel> groupModels = groupModelAssembler.toCollectionModel(user.getGroups())
                .removeLinks()
                .add(instantiateLinks.linkToUsersGroupAssociation(userId, "association"));

        groupModels.getContent().forEach(groupModel -> {
            groupModel.add(instantiateLinks.linkToUsersGroupRemoveAssociation(
                    userId, groupModel.getId(), "removeAssociation"));
        });

        return groupModels;
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity association(@PathVariable Long userId, @PathVariable Long groupId) {
        registrationUser.addAssociationGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity removeAssociation(@PathVariable Long userId, @PathVariable Long groupId) {
        registrationUser.removeAssociationGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }
}
