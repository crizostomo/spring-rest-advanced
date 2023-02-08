package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerRestaurant;
import com.developer.beverageapi.api.v1.model.RestaurantBasicModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBasicModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public RestaurantBasicModelAssembler() {
        super(ControllerRestaurant.class, RestaurantBasicModel.class);
    }

    @Override
    public RestaurantBasicModel toModel(Restaurant restaurant) {
        RestaurantBasicModel restaurantBasicModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantBasicModel);

        if (security.allowedToConsultRestaurants()) {
            restaurantBasicModel.add(instantiateLinks.linkToRestaurants("restaurants"));
        }

        if (security.allowedToConsultKitchens()) {
            restaurantBasicModel.getKitchen().add(instantiateLinks.linkToKitchen(restaurant.getKitchen().getId()));
        }

        return restaurantBasicModel;
    }

    public CollectionModel<RestaurantBasicModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantBasicModel> collectionModel = super.toCollectionModel(entities);

        if (security.allowedToConsultRestaurants()) {
            collectionModel.add(instantiateLinks.linkToRestaurants());
        }

        return collectionModel;
    }
}
