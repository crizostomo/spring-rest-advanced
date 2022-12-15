package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.ControllerRestaurant;
import com.developer.beverageapi.api.model.RestaurantBasicModel;
import com.developer.beverageapi.api.model.RestaurantSummaryModel;
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

    public RestaurantSummaryModelAssembler() {
        super(ControllerRestaurant.class, RestaurantSummaryModel.class);
    }

    @Override
    public RestaurantSummaryModel toModel(Restaurant restaurant) {
        RestaurantSummaryModel restaurantSummaryModel = createModelWithId(restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantSummaryModel);

        restaurantSummaryModel.add(instantiateLinks.linkToRestaurants("restaurants"));

        return restaurantSummaryModel;
    }

    public CollectionModel<RestaurantSummaryModel> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(instantiateLinks.linkToRestaurants());
    }
}
