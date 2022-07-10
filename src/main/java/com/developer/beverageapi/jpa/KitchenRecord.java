package com.developer.beverageapi.jpa;

import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class KitchenRecord {

    @PersistenceContext
    private EntityManager manager;

    public List<Kitchen> list(){
        return manager.createQuery("from Kitchen", Kitchen.class)
                .getResultList();
    }

    public Kitchen search(Long id){
        return manager.find(Kitchen.class, id);
    }

    @Transactional
    public Kitchen save(Kitchen kitchen){
        return manager.merge(kitchen);
    }

    @Transactional
    public void remove(Kitchen kitchen){
        kitchen = search(kitchen.getId());
        manager.remove(kitchen);
    }
}
