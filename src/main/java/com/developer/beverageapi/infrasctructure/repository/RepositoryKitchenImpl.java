package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RepositoryKitchenImpl implements RepositoryKitchen {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Kitchen> listAll(){
        return manager.createQuery("from Kitchen", Kitchen.class)
                .getResultList();
    }

    @Override
    public Kitchen searchById(Long id){
        return manager.find(Kitchen.class, id);
    }

    @Override
    @Transactional
    public Kitchen add(Kitchen kitchen){
        return manager.merge(kitchen);
    }

    @Override
    @Transactional
    public void remove(Kitchen kitchen){
        kitchen = searchById(kitchen.getId());
        manager.remove(kitchen);
    }
}
