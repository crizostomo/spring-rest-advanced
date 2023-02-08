package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerState;
import com.developer.beverageapi.api.v1.model.StateModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class StateModelAssembler extends RepresentationModelAssemblerSupport<State, StateModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public StateModelAssembler() {
        super(ControllerState.class, StateModel.class);
    }

    public StateModel toModel(State state) {
        StateModel stateModel = createModelWithId(state.getId(), state);

        modelMapper.map(state, stateModel);

        if (security.allowedToConsultStates()) {
            stateModel.add(instantiateLinks.linkToStates("states"));
        }

        return stateModel;
    }

    @Override
    public CollectionModel<StateModel> toCollectionModel(Iterable<? extends State> entities) {
        CollectionModel<StateModel> collectionModel = super.toCollectionModel(entities);

        if (security.allowedToConsultStates()) {
            collectionModel.add(instantiateLinks.linkToStates());
        }

        return collectionModel;
    }
}
