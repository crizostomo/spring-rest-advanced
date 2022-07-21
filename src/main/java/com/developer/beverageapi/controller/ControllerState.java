package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
public class ControllerState {

    @Autowired
    private RepositoryState repositoryState;

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


}
