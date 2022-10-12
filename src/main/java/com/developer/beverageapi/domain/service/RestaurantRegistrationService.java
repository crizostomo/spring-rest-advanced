package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.RestaurantNotFoundException;
import com.developer.beverageapi.domain.model.*;
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

    @Autowired
    private CityRegistrationService cityRegistrationService;

    @Autowired
    private PaymentRegistrationService paymentRegistrationService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Transactional
    public Restaurant add(Restaurant restaurant) {

        Long kitchenId = restaurant.getKitchen().getId();
        Long cityId = restaurant.getAddress().getCity().getId();

        Kitchen kitchen = registrationKitchen.searchOrFail(kitchenId);
        City city = cityRegistrationService.searchOrFail(cityId);

        restaurant.setKitchen(kitchen);
        restaurant.getAddress().setCity(city);

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


    @Transactional
    public void open(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.open();
    }

    @Transactional
    public void close(Long restaurantId) {
        Restaurant currentRestaurant = searchOrFail(restaurantId);

        currentRestaurant.close();
    }

    @Transactional
    public void removeAssociationWithPayments(Long restaurantId, Long paymentId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        Payment payment = paymentRegistrationService.searchOrFail(paymentId);

        restaurant.removePayment(payment);
    }

    @Transactional
    public void associationWithPayments(Long restaurantId, Long paymentId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        Payment payment = paymentRegistrationService.searchOrFail(paymentId);

        restaurant.addPayment(payment);
    }

    @Transactional
    public void removeAssociationWithResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        User user = userRegistrationService.searchOrFail(userId);

        restaurant.removeResponsible(user);
    }

    @Transactional
    public void associationWithResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = searchOrFail(restaurantId);
        User user = userRegistrationService.searchOrFail(userId);

        restaurant.addResponsible(user);
    }

    public Restaurant searchOrFail(Long restaurantId) {
        return repositoryRestaurant.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}
