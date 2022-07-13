package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryState;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RepositoryStateImpl implements RepositoryState {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<State> listAll(){
        return manager.createQuery("from State", State.class)
                .getResultList();
    }

    @Override
    public State searchById(Long id){
        return manager.find(State.class, id);
    }

    @Override
    @Transactional
    public State add(State state){
        return manager.merge(state);
    }

    @Override
    @Transactional
    public void remove(State state){
        state = searchById(state.getId());
        manager.remove(state);
    }
}
