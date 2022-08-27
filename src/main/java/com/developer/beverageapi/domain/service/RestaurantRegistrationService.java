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

    public static final String MSG_RESTAURANT_NOT_FOUND
            = "There is no Restaurant with the code %d";

    public static final String MSG_RESTAURANT_BEING_USED
            = "Restaurant with the code %d cannot be removed, because it is being used";

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    public Restaurant add(Restaurant restaurant) {

        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = repositoryKitchen
                .findById(kitchenId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MSG_RESTAURANT_NOT_FOUND, kitchenId)));

        restaurant.setKitchen(kitchen);

        return repositoryRestaurant.save(restaurant);
    }

    public void remove(Long restaurantId) {
        try {
            repositoryRestaurant.deleteById(restaurantId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format(MSG_RESTAURANT_NOT_FOUND, restaurantId));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_RESTAURANT_BEING_USED, restaurantId));
        }
    }

    public Restaurant searchOrFail(Long restaurantId) {
        return repositoryRestaurant.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MSG_RESTAURANT_NOT_FOUND, restaurantId)));
    }
}
