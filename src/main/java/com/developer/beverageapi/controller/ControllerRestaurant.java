package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class ControllerRestaurant {

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @GetMapping
    public List<Restaurant> list(){
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
}
