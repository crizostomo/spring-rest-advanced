package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.UserModelAssembler;
import com.developer.beverageapi.api.model.UserModel;
import com.developer.beverageapi.api.swaggerapi.controller.ControllerRestaurantUserResponsibleOpenApi;
import com.developer.beverageapi.domain.exception.UserNotFoundException;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantUserResponsible implements ControllerRestaurantUserResponsibleOpenApi {

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @GetMapping
    public CollectionModel<UserModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        if (restaurant.getResponsible().isEmpty()) {
            throw new UserNotFoundException("The user was not found");
        }

        return userModelAssembler.toCollectionModel(restaurant.getResponsible())
                .removeLinks()
                .add(WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder
                                .methodOn(ControllerRestaurantUserResponsible.class)
                                .list(restaurantId))
                        .withSelfRel());
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void association(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registrationRestaurant.associationWithResponsible(restaurantId, userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssociation(@PathVariable Long restaurantId, @PathVariable Long userId) {
        registrationRestaurant.removeAssociationWithResponsible(restaurantId, userId);
    }
}
