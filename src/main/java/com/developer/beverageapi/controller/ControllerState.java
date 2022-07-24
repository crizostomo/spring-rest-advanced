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

import java.util.List;

@RestController
@RequestMapping("/states")
public class ControllerState {

    @Autowired
    private RepositoryState repositoryState;

    @Autowired
    private StateRegistrationService registrationState;

    @GetMapping
    public List<State> list(){
        return repositoryState.listAll();
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<State> search(@PathVariable Long stateId) {
        State state = repositoryState.searchById(stateId);

        if (state != null) {
            return ResponseEntity.status(HttpStatus.OK).body(state);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State add(@RequestBody State state){
        return repositoryState.add(state);
    }

    @PutMapping("/{stateId}")
    public ResponseEntity<State> update(@PathVariable Long stateId,
                                        @RequestBody State state){
        State currentState = repositoryState.searchById(stateId);

        if (currentState != null) {
            BeanUtils.copyProperties(state, currentState, "id");

            currentState = registrationState.add(currentState);
            return ResponseEntity.ok(currentState);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{stateId}")
    public ResponseEntity<State> delete(@PathVariable Long stateId){
        try {
            registrationState.remove(stateId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
