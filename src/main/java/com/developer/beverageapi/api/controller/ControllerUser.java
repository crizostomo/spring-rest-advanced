package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.UserInputDismantle;
import com.developer.beverageapi.api.assembler.UserModelAssembler;
import com.developer.beverageapi.api.model.UserModel;
import com.developer.beverageapi.api.model.input.UserPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithoutPasswordInput;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.repository.RepositoryUser;
import com.developer.beverageapi.domain.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ControllerUser {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private UserRegistrationService registrationUser;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDismantle userInputDismantle;

    @GetMapping
    public List<UserModel> list() {
        List<User> userList = repositoryUser.findAll();

        return userModelAssembler.toCollectionModel(userList);
    }

    @GetMapping("/{userId}")
    public UserModel search(@PathVariable Long userId) {
        User user = registrationUser.searchOrFail(userId);

        return userModelAssembler.toModel(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel add(@RequestBody @Valid UserWithPasswordInput userWithPasswordInput) {
        User user = userInputDismantle.toDomainObjectWithPassword(userWithPasswordInput);

        user = registrationUser.add(user);

        return userModelAssembler.toModel(user);
    }

    @PutMapping("/{userId}")
    public UserModel update(@PathVariable Long userId,
                             @RequestBody @Valid UserWithoutPasswordInput userWithoutPasswordInput) {
        User currentUser = registrationUser.searchOrFail(userId);

        userInputDismantle.copyToDomainObjectWithoutPassword(userWithoutPasswordInput, currentUser);

        currentUser = registrationUser.add(currentUser);

        return userModelAssembler.toModel(currentUser);
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long userId,
                            @RequestBody @Valid UserPasswordInput password) {

        registrationUser.updatePassword(userId, password.getCurrentPassword(), password.getNewPassword());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        registrationUser.remove(userId);
    }
}
