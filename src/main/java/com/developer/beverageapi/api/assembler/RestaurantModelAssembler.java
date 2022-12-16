package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.ControllerRestaurant;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.api.model.RestaurantModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public RestaurantModelAssembler() {
        super(ControllerRestaurant.class, RestaurantModel.class);
    }

    @Override
    public RestaurantModel toModel(Restaurant restaurant) {
        RestaurantModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantModel);

        restaurantModel.add(instantiateLinks.linkToRestaurants("restaurants"));

//        restaurantModel.getKitchen().add(instantiateLinks.linkToCity(restaurant.getAddress().getCity().getId()));

        restaurantModel.add(instantiateLinks.linkToRestaurantPayment(restaurant.getId(), "payments"));

        restaurantModel.add(instantiateLinks.linkToRestaurantResponsible(restaurantModel.getId(), "responsible"));

        if (restaurant.activationAllowed()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "active"));
        }

        if (restaurant.inactivationAllowed()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "inactive"));
        }

        if (restaurant.openingAllowed()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "open"));
        }

        if (restaurant.closingAllowed()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "close"));
        }

        return restaurantModel;
    }

    public CollectionModel<RestaurantModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(instantiateLinks.linkToRestaurants());
    }
}
