package com.developer.beverageapi.assembler;

import com.developer.beverageapi.domain.model.KitchenModel;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.model.RestaurantModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestaurantModel toModel(Restaurant restaurant) {
//        KitchenModel kitchenModel = new KitchenModel();
//        kitchenModel.setId(restaurant.getKitchen().getId());
//        kitchenModel.setName(restaurant.getKitchen().getName());
//
//        RestaurantModel restaurantModel = new RestaurantModel();
//        restaurantModel.setId(restaurant.getId());
//        restaurantModel.setName(restaurant.getName());
//        restaurantModel.setDelivery(restaurant.getDelivery());
//        restaurantModel.setKitchen(kitchenModel);
//        return restaurantModel;

        return modelMapper.map(restaurant, RestaurantModel.class);

    }

    public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> toModel(restaurant))
                .collect(Collectors.toList());
    }
}
