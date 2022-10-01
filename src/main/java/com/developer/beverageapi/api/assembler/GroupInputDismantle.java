package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.input.GroupInput;
import com.developer.beverageapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public Group toDomainObject(GroupInput groupInput) {

        return modelMapper.map(groupInput, Group.class);
    }

    public void copyToDomainObject(GroupInput groupInput, Group group) {
        modelMapper.map(groupInput, group);
    }
}
