package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.GroupModelAssembler;
import com.developer.beverageapi.api.model.GroupModel;
import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/groups")
public class ControllerUserGroup {

    @Autowired
    private UserRegistrationService registrationUser;

    @Autowired
    private GroupModelAssembler groupModelAssembler;

    @GetMapping
    public List<GroupModel> list(@PathVariable Long userId) {
        User user = registrationUser.searchOrFail(userId);

        return groupModelAssembler.toCollectionModel(user.getGroups());
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void association(@PathVariable Long userId, @PathVariable Long groupId) {
        registrationUser.addAssociationGroup(userId, groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssociation(@PathVariable Long userId, @PathVariable Long groupId) {
        registrationUser.removeAssociationGroup(userId, groupId);
    }
}