package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.StateNotFoundException;
import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StateRegistrationService {

    public static final String MSG_STATE_BEING_USED = "State with the code %d cannot be found" +
            "because it is being used";

    @Autowired
    private RepositoryState repositoryState;

    @Transactional
    public State add(State state){
        return repositoryState.save(state);
    }

    @Transactional
    public void remove(Long stateId){
        try{
            repositoryState.deleteById(stateId);
            repositoryState.flush();

        } catch (EmptyResultDataAccessException e){
            throw new StateNotFoundException(stateId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_STATE_BEING_USED, stateId));
        }
    }

    public State searchOrFail(Long stateId) {
        return repositoryState.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }
}
