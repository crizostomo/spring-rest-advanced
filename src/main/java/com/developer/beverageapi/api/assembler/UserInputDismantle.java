package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.input.UserWithPasswordInput;
import com.developer.beverageapi.api.model.input.UserWithoutPasswordInput;
import com.developer.beverageapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObjectWithPassword(UserWithPasswordInput userWithPasswordInput) {

        return modelMapper.map(userWithPasswordInput, User.class);
    }

    public void copyToDomainObjectWithPassword(UserWithPasswordInput userWithPasswordInput, User user) {
        modelMapper.map(userWithPasswordInput, user);
    }

    public User toDomainObjectWithoutPassword(UserWithoutPasswordInput userWithoutPasswordInput) {

        return modelMapper.map(userWithoutPasswordInput, User.class);
    }

    public void copyToDomainObjectWithoutPassword(UserWithoutPasswordInput userWithoutPasswordInput, User user) {
        modelMapper.map(userWithoutPasswordInput, user);
    }
}
