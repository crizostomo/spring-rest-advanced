package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.assembler.StateInputDismantle;
import com.developer.beverageapi.api.v1.assembler.StateModelAssembler;
import com.developer.beverageapi.api.v1.model.StateModel;
import com.developer.beverageapi.api.v1.model.input.StateInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerStateOpenApi;
import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.StateRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerState implements ControllerStateOpenApi {

    @Autowired
    private RepositoryState repositoryState;

    @Autowired
    private StateRegistrationService registrationState;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @Autowired
    private StateInputDismantle stateInputDismantle;

    @GetMapping
    public CollectionModel<StateModel> list() {
        List<State> allStates = repositoryState.findAll();

        return stateModelAssembler.toCollectionModel(allStates);
    }

    @GetMapping("/{stateId}")
    public StateModel search(@PathVariable Long stateId) {
        State state = registrationState.searchOrFail(stateId);

        return stateModelAssembler.toModel(state);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateModel add(@RequestBody @Valid StateInput stateInput) {
        State state = stateInputDismantle.toDomainObject(stateInput);

        state = registrationState.add(state);

        return stateModelAssembler.toModel(state);
    }

    @PutMapping("/{stateId}")
    public StateModel update(@PathVariable Long stateId,
                             @RequestBody @Valid StateInput stateInput) {
        State currentState = registrationState.searchOrFail(stateId);

        stateInputDismantle.copyToDomainObject(stateInput, currentState);

        currentState = registrationState.add(currentState);

        return stateModelAssembler.toModel(currentState);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long stateId) {
        registrationState.remove(stateId);
    }
}
