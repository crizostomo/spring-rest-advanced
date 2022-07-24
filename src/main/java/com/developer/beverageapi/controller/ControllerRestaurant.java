package com.developer.beverageapi.controller;

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

@RestController
@RequestMapping("/restaurants")
public class ControllerRestaurant {

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

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

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {

        try {
            restaurant = repositoryRestaurant.add(restaurant);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restaurant);
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> update(@PathVariable Long restaurantId,
                                             @RequestBody Restaurant restaurant){
        Restaurant currentRestaurant = repositoryRestaurant.searchById(restaurantId);

        try {
            if (currentRestaurant != null) {
                BeanUtils.copyProperties(restaurant, currentRestaurant, "id");

                currentRestaurant = registrationRestaurant.add(currentRestaurant);
                return ResponseEntity.ok(currentRestaurant);
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

//    @DeleteMapping("/{restaurantId}")
//    public ResponseEntity<Restaurant> delete(@PathVariable Long restaurantId){
//        try{
//            registrationRestaurant
//        }catch (
//
//        )
//    }
}
