package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.ControllerCity;
import com.developer.beverageapi.api.controller.ControllerState;
import com.developer.beverageapi.api.controller.ControllerUser;
import com.developer.beverageapi.api.controller.ControllerUserGroup;
import com.developer.beverageapi.api.model.CityModel;
import com.developer.beverageapi.api.model.UserModel;
import com.developer.beverageapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public UserModelAssembler() {
        super(ControllerUser.class, UserModel.class);
    }

    public UserModel toModel(User user) {
        UserModel userModel = createModelWithId(user.getId(), user);

        modelMapper.map(user, userModel);

        userModel.add(instantiateLinks.linkToUsers("users"));

        userModel.add(instantiateLinks.linkToUserGroups(userModel.getId(), "users-group"));

        return userModel;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        return super.toCollectionModel(entities)
                .add(WebMvcLinkBuilder.linkTo(ControllerUser.class).withSelfRel());
    }
}
