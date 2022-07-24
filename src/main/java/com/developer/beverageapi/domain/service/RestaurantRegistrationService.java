package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public void remove(Long restaurantId){
        try {
            repositoryRestaurant.remove(restaurantId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("There is no Restaurant with the code %d",
                            restaurantId));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Restaurant with the code %d cannot be removed," +
                            "because it is being used", restaurantId));
        }
    }
}
