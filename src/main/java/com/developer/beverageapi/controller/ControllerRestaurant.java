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
    public ResponseEntity<Restaurant> search(@PathVariable Long restaurantId) {
        Optional<Restaurant> restaurant = repositoryRestaurant.findById(restaurantId);

        if (restaurant.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(restaurant.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {

        try {
            restaurant = repositoryRestaurant.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurant);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> update(@PathVariable Long restaurantId,
                                             @RequestBody Restaurant restaurant) {
        Optional<Restaurant> currentRestaurant = repositoryRestaurant.findById(restaurantId);

        try {
            if (currentRestaurant.isPresent()) {
                BeanUtils.copyProperties(restaurant, currentRestaurant.get(),
                        "id", "payment", "address");

                Restaurant savedRestaurant = registrationRestaurant.add(currentRestaurant.get());
                return ResponseEntity.ok(savedRestaurant);
            }

            return ResponseEntity.notFound().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long restaurantId,
                                           @RequestBody Map<String, Object> fields){
        Optional<Restaurant> currentRestaurant = repositoryRestaurant.findById(restaurantId);

        if (currentRestaurant.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        merge(fields, currentRestaurant.get());

        return update(restaurantId, currentRestaurant.get());
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
