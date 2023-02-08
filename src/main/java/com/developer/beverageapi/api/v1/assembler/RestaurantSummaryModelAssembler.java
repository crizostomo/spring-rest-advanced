package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerRestaurant;
import com.developer.beverageapi.api.v1.model.RestaurantSummaryModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantSummaryModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public RestaurantSummaryModelAssembler() {
        super(ControllerRestaurant.class, RestaurantSummaryModel.class);
    }

    @Override
    public RestaurantSummaryModel toModel(Restaurant restaurant) {
        RestaurantSummaryModel restaurantSummaryModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantSummaryModel);

        if (security.allowedToConsultRestaurants()) {
            restaurantSummaryModel.add(instantiateLinks.linkToRestaurants("restaurants"));
        }

        return restaurantSummaryModel;
    }

    public CollectionModel<RestaurantSummaryModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantSummaryModel> collectionModel = super.toCollectionModel(entities);

        if (security.allowedToConsultRestaurants()) {
            collectionModel.add(instantiateLinks.linkToRestaurants());
        }
        return collectionModel;
    }
}
