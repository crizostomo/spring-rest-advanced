package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRegistrationService {

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    public Restaurant add(Restaurant restaurant){

        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = repositoryKitchen.searchById(kitchenId);

        if (kitchen == null){
            throw new EntityNotFoundException(
                    String.format("There is no kitchen registry with code %d", kitchenId));
        }

        restaurant.setKitchen(kitchen);

        return repositoryRestaurant.add(restaurant);
    }
}
