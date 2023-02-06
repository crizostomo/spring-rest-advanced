package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.UserInputDismantle;
import com.developer.beverageapi.api.v1.assembler.UserModelAssembler;
import com.developer.beverageapi.api.v1.model.UserModel;
import com.developer.beverageapi.api.v1.model.input.UserPasswordInput;
import com.developer.beverageapi.api.v1.model.input.UserWithPasswordInput;
import com.developer.beverageapi.api.v1.model.input.UserWithoutPasswordInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerUserOpenApi;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.repository.RepositoryUser;
import com.developer.beverageapi.domain.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerUser implements ControllerUserOpenApi {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private UserRegistrationService registrationUser;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDismantle userInputDismantle;

    @CheckSecurity.UsersGroupsPermissions.AllowedToConsult
    @GetMapping
    public CollectionModel<UserModel> list() {
        List<User> userList = repositoryUser.findAll();

        return userModelAssembler.toCollectionModel(userList);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToConsult
    @GetMapping("/{userId}")
    public UserModel search(@PathVariable Long userId) {
        User user = registrationUser.searchOrFail(userId);

        return userModelAssembler.toModel(user);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel add(@RequestBody @Valid UserWithPasswordInput userWithPasswordInput) {
        User user = userInputDismantle.toDomainObjectWithPassword(userWithPasswordInput);

        user = registrationUser.add(user);

        return userModelAssembler.toModel(user);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToChangeUser
    @PutMapping("/{userId}")
    public UserModel update(@PathVariable Long userId,
                             @RequestBody @Valid UserWithoutPasswordInput userWithoutPasswordInput) {
        User currentUser = registrationUser.searchOrFail(userId);

        userInputDismantle.copyToDomainObjectWithoutPassword(userWithoutPasswordInput, currentUser);

        currentUser = registrationUser.add(currentUser);

        return userModelAssembler.toModel(currentUser);
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToChangeOwnPassword
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long userId,
                            @RequestBody @Valid UserPasswordInput password) {

        registrationUser.updatePassword(userId, password.getCurrentPassword(), password.getNewPassword());
    }

    @CheckSecurity.UsersGroupsPermissions.AllowedToEdit
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        registrationUser.remove(userId);
    }
}
