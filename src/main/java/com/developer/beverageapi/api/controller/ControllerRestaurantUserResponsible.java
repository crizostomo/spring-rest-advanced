package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.assembler.UserModelAssembler;
import com.developer.beverageapi.api.model.UserModel;
import com.developer.beverageapi.api.swaggerapi.controller.ControllerRestaurantUserResponsibleOpenApi;
import com.developer.beverageapi.domain.exception.UserNotFoundException;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantUserResponsible implements ControllerRestaurantUserResponsibleOpenApi {

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private InstantiateLinks instantiateLinks;

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

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity association(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registrationRestaurant.associationWithResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity removeAssociation(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registrationRestaurant.removeAssociationWithResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
