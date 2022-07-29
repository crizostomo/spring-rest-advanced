package com.developer.beverageapi.controller;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;


    /**
     * @RequestParam is used when you want to search by something typing directly on the query
     * you can put the ("name") after just to be clearer, however you don't need to type it
     * An example: https://localhost:8080/test/kitchens/by-name?name=Brazilian
     */

    @GetMapping("/kitchens/by-name")
    public List<Kitchen> kitchensByName(@RequestParam("name") String name){ //@RequestParam("name") is optional
        return repositoryKitchen.findByNameContaining(name);
    }

    @GetMapping("/kitchens/by-only-name")
    public Optional<Kitchen> kitchenByName(@RequestParam("name") String name){ //@RequestParam("name") is optional
        return repositoryKitchen.findOneByName(name);
    }

    @GetMapping("/restaurants/by-delivery-fee")
    public List<Restaurant> restaurantsByDeliveryFee(BigDecimal initialFee, BigDecimal finalFee){
        return repositoryRestaurant.findByDeliveryBetween(initialFee, finalFee);
    }

    @GetMapping("/restaurants/by-name")
    public List<Restaurant> restaurantsByNameAndKitchenId(String name, Long kitchenId){
        return repositoryRestaurant.findByNameContainingAndKitchenId(name, kitchenId);
    }
}
