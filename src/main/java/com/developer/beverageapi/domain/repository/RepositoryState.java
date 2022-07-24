package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.State;

import java.util.List;

public interface RepositoryState {

    List<State> listAll();
    State searchById(Long id);
    State add(State state);
    void remove(Long id);
}
