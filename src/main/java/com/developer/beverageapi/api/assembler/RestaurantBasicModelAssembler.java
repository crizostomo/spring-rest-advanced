package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.ControllerRestaurant;
import com.developer.beverageapi.api.model.RestaurantBasicModel;
import com.developer.beverageapi.api.model.RestaurantModel;
import com.developer.beverageapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantBasicModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public RestaurantBasicModelAssembler () {
        super(ControllerRestaurant.class, RestaurantBasicModel.class);
    }

    @Override
    public RestaurantBasicModel toModel(Restaurant restaurant) {
        RestaurantBasicModel restaurantBasicModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantBasicModel);

        restaurantBasicModel.add(instantiateLinks.linkToRestaurants("restaurants"));

        restaurantBasicModel.getKitchen().add(instantiateLinks.linkToKitchen(restaurant.getKitchen().getId()));

        return restaurantBasicModel;
    }

    public CollectionModel<RestaurantBasicModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(instantiateLinks.linkToRestaurants());
    }
}
