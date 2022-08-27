package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class ControllerRestaurant {

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @GetMapping
    public List<Restaurant> list() {
        return repositoryRestaurant.findAll();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant search(@PathVariable Long restaurantId) {
        return registrationRestaurant.searchOrFail(restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant add(@RequestBody Restaurant restaurant) {
        return registrationRestaurant.add(restaurant);
    }


    @PutMapping("/{restaurantId}")
    public Restaurant update(@PathVariable Long restaurantId,
                             @RequestBody Restaurant restaurant) {
        Restaurant currentRestaurant = registrationRestaurant.searchOrFail(restaurantId);

        BeanUtils.copyProperties(restaurant, currentRestaurant,
                "id", "payment", "address", "recordDate");

        return registrationRestaurant.add(currentRestaurant);
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant partialUpdate(@PathVariable Long restaurantId,
                                    @RequestBody Map<String, Object> fields) {
        Restaurant currentRestaurant = registrationRestaurant.searchOrFail(restaurantId);

        merge(fields, currentRestaurant);

        return update(restaurantId, currentRestaurant);
    }

    private void merge(Map<String, Object> sourceData, Restaurant destinyRestaurant) { //destinyRestaurant = currentRestaurant
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant sourceRestaurant = objectMapper.convertValue(sourceData, Restaurant.class);

        sourceData.forEach((propertyName, propertyValue) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
            field.setAccessible(true); //Here, we "break" the private method in restaurant by accessing it

            Object newValue = ReflectionUtils.getField(field, sourceRestaurant);

            System.out.println(propertyName + " = " + propertyValue);

            ReflectionUtils.setField(field, destinyRestaurant, newValue);
        });
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long restaurantId) {
        registrationRestaurant.remove(restaurantId);
    }
}
