package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositoryKitchenImpl implements RepositoryKitchen {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Kitchen> listAll(){
        return manager.createQuery("from Kitchen", Kitchen.class)
                .getResultList();
    }

    @Override
    public List<Kitchen> searchByName(String name) {
        return manager.createQuery("from Kitchen where name like :name", Kitchen.class) //like = it contains part of the name
                .setParameter("name", "%" + name + "%") //We need to inform the double "%" because they are like the '*' when we are searching
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
    public void remove(Long id){
        Kitchen kitchen = searchById(id);

        if (kitchen == null){
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(kitchen);
    }
}
