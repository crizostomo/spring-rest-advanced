package com.developer.beverageapi.jpa;

import com.developer.beverageapi.domain.model.Kitchen;
import org.springframework.stereotype.Component;

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

    public Kitchen add(Kitchen kitchen){
        return manager.merge(kitchen);
    }
}
