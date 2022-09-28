package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.api.model.input.RestaurantInput;
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

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
        // To avoid org.hinernate.HibernateExeception: identifier of an instance of
        // com.beverage.domain.model.Kitchen was altered from 1 to 2
        restaurant.setKitchen(new Kitchen());

        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }

        modelMapper.map(restaurantInput, restaurant);
    }
}
