package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.input.StateInput;
import com.developer.beverageapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public State toDomainObject(StateInput stateInput) {

        return modelMapper.map(stateInput, State.class);
    }

    public void copyToDomainObject(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }
}
