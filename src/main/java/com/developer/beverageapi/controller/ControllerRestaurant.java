package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        return repositoryRestaurant.listAll();
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> search(@PathVariable Long restaurantId) {
        Restaurant restaurant = repositoryRestaurant.searchById(restaurantId);

        if (restaurant != null) {
            return ResponseEntity.status(HttpStatus.OK).body(restaurant);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {

        try {
            restaurant = repositoryRestaurant.add(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurant);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> update(@PathVariable Long restaurantId,
                                             @RequestBody Restaurant restaurant) {
        Restaurant currentRestaurant = repositoryRestaurant.searchById(restaurantId);

        try {
            if (currentRestaurant != null) {
                BeanUtils.copyProperties(restaurant, currentRestaurant, "id");

                currentRestaurant = registrationRestaurant.add(currentRestaurant);
                return ResponseEntity.ok(currentRestaurant);
            }

            return ResponseEntity.notFound().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long restaurantId,
                                           @RequestBody Map<String, Object> fields){
        Restaurant currentRestaurant = repositoryRestaurant.searchById(restaurantId);

        if (currentRestaurant == null){
            return ResponseEntity.notFound().build();
        }

        merge(fields, currentRestaurant);

        return update(restaurantId, currentRestaurant);
    }

    private void merge(Map<String, Object> fieldSource, Restaurant destinyRestaurant) {
        fieldSource.forEach((propertyName, propertyValue) -> {

            System.out.println(propertyName + " = " + propertyValue);
        });
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long restaurantId) {
        try {
            registrationRestaurant.remove(restaurantId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
