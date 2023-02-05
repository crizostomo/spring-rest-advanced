package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.assembler.UserModelAssembler;
import com.developer.beverageapi.api.v1.model.UserModel;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerRestaurantUserResponsibleOpenApi;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantUserResponsible implements ControllerRestaurantUserResponsibleOpenApi {

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @CheckSecurity.Restaurants.AllowedToConsult
    @GetMapping
    public CollectionModel<UserModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        CollectionModel<UserModel> userModels = userModelAssembler
                .toCollectionModel(restaurant.getResponsible())
                .removeLinks()
                .add(instantiateLinks.linkToRestaurantResponsible(restaurantId))
                .add(instantiateLinks.linkToRestaurantResponsibleAssociation(restaurantId, "association"));

        userModels.getContent().stream().forEach(userModel -> {
            userModel.add(instantiateLinks.linkToRestaurantResponsibleRemoveAssociation(
                    restaurantId, userModel.getId(), "removeAssociation"
            ));
        });

        return userModels;
    }

    @CheckSecurity.Restaurants.AllowedToManageRecord
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity association(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registrationRestaurant.associationWithResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.AllowedToManageRecord
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity removeAssociation(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registrationRestaurant.removeAssociationWithResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
