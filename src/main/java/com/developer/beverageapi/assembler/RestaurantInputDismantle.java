package com.developer.beverageapi.assembler;

import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.model.RestaurantModel;
import com.developer.beverageapi.domain.model.input.RestaurantInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName(restaurantInput.getName());
//        restaurant.setDelivery(restaurantInput.getDelivery());
//
//        Kitchen kitchen = new Kitchen();
//        kitchen.setId(restaurantInput.getKitchen().getId());
//
//        restaurant.setKitchen(kitchen);
//
//        return restaurant;

        return modelMapper.map(restaurantInput, Restaurant.class);
    }
}
