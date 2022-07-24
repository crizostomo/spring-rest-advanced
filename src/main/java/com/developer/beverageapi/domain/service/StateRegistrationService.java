package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StateRegistrationService {

    @Autowired
    private RepositoryState repositoryState;

    public State add(State state){
        return repositoryState.add(state);
    }

    public void remove(Long stateId){
        try{
            repositoryState.remove(stateId);

        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(
                    String.format("There is no State with the code %d",
                            stateId));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("State with the code %d cannot be found" +
                            "because it is being used", stateId));
        }
    }
}
