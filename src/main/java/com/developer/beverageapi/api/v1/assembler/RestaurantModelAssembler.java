package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerRestaurant;
import com.developer.beverageapi.api.v1.model.RestaurantModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public RestaurantModelAssembler() {
        super(ControllerRestaurant.class, RestaurantModel.class);
    }

    @Override
    public RestaurantModel toModel(Restaurant restaurant) {
        RestaurantModel restaurantModel = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantModel);

        if (security.allowedToConsultRestaurants()) {
            restaurantModel.add(instantiateLinks.linkToRestaurants("restaurants"));
        }

        if (security.allowedToManageRecordRestaurants()) {
            if (restaurant.activationAllowed()) {
                restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "active"));
            }

            if (restaurant.inactivationAllowed()) {
                restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "inactive"));
            }
        }

        if (security.allowedToManageRestaurantOperation(restaurant.getId())) {
            if (restaurant.openingAllowed()) {
                restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "open"));
            }

            if (restaurant.closingAllowed()) {
                restaurantModel.add(instantiateLinks.linkToRestaurantActive(restaurantModel.getId(), "close"));
            }
        }

        if (security.allowedToConsultRestaurants()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantResponsible(restaurantModel.getId(), "products"));
        }

        if (security.allowedToConsultKitchens()) {
            restaurantModel.getKitchen().add(instantiateLinks.linkToKitchen(restaurant.getKitchen().getId()));
        }

        if (security.allowedToConsultCities()) {
            if (restaurantModel.getAddress() != null && restaurantModel.getAddress().getCity() != null) {
                restaurantModel.getAddress().getCity().add(instantiateLinks.linkToCity(restaurant.getAddress().getCity().getId()));
            }
        }

//        restaurantModel.getKitchen().add(instantiateLinks.linkToCity(restaurant.getAddress().getCity().getId()));

        if (security.allowedToConsultRestaurants()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantPayment(restaurant.getId(), "payments"));
        }

        if (security.allowedToManageRecordRestaurants()) {
            restaurantModel.add(instantiateLinks.linkToRestaurantResponsible(restaurantModel.getId(), "responsible"));
        }

        return restaurantModel;
    }

    public CollectionModel<RestaurantModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantModel> collectionModel = super.toCollectionModel(entities);

        if (security.allowedToConsultRestaurants()) {
            collectionModel.add(instantiateLinks.linkToRestaurants());
        }

        return collectionModel;
    }
}
