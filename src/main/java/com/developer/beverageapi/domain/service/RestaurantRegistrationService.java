package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.RestaurantNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantRegistrationService {

    public static final String MSG_RESTAURANT_BEING_USED
            = "Restaurant with the code %d cannot be removed, because it is being used";

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private KitchenRegistrationService registrationKitchen;

    @Transactional
    public Restaurant add(Restaurant restaurant) {

        Long kitchenId = restaurant.getKitchen().getId();

        Kitchen kitchen = registrationKitchen.searchOrFail(kitchenId);

        restaurant.setKitchen(kitchen);

        return repositoryRestaurant.save(restaurant);
    }

    @Transactional
    public void remove(Long restaurantId) {
        try {
            repositoryRestaurant.deleteById(restaurantId);
            repositoryRestaurant.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(restaurantId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_RESTAURANT_BEING_USED, restaurantId));
        }
    }

    @Transactional
    public void active(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.active();
    }

    @Transactional
    public void inactive(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.inactive();
    }

    public Restaurant searchOrFail(Long restaurantId) {
        return repositoryRestaurant.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}
