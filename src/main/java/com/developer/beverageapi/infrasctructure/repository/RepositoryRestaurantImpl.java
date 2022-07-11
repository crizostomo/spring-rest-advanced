package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class RepositoryRestaurantImpl implements RepositoryRestaurant {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurant> listAll(){
        return manager.createQuery("from Restaurant", Restaurant.class)
                .getResultList();
    }

    @Override
    public Restaurant searchById(Long id){
        return manager.find(Restaurant.class, id);
    }

    @Override
    @Transactional
    public Restaurant add(Restaurant restaurant){
        return manager.merge(restaurant);
    }

    @Override
    @Transactional
    public void remove(Restaurant restaurant){
        restaurant = searchById(restaurant.getId());
        manager.remove(restaurant);
    }
}
