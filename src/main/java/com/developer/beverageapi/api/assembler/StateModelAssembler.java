package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.StateModel;
import com.developer.beverageapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public StateModel toModel(State state) {

        return modelMapper.map(state, StateModel.class);
    }

    public List<StateModel> toCollectionModel(List<State> states) {
        return states.stream()
                .map(state -> toModel(state))
                .collect(Collectors.toList());
    }
}
