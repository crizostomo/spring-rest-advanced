package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryState;
import com.developer.beverageapi.domain.service.StateRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/states")
public class ControllerState {

    @Autowired
    private RepositoryState repositoryState;

    @Autowired
    private StateRegistrationService registrationState;

    @GetMapping
    public List<State> list() {
        return repositoryState.findAll();
    }

    @GetMapping("/{stateId}")
    public State search(@PathVariable Long stateId) {
        return registrationState.searchOrFail(stateId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State add(@RequestBody @Valid State state) {
        return registrationState.add(state);
    }

    @PutMapping("/{stateId}")
    public State update(@PathVariable Long stateId,
                                        @RequestBody @Valid State state) {
        State currentState = registrationState.searchOrFail(stateId);

        BeanUtils.copyProperties(state, currentState, "id");

        return registrationState.add(currentState);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long stateId) {
        registrationState.remove(stateId);
    }
}
