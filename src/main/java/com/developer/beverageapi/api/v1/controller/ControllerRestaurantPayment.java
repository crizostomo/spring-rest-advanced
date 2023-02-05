package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.assembler.PaymentModelAssembler;
import com.developer.beverageapi.api.v1.model.PaymentModel;
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
@RequestMapping(path = "/v1/restaurants/{restaurantId}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantPayment {

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @CheckSecurity.Restaurants.AllowedToConsult
    @GetMapping
    public CollectionModel<PaymentModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        CollectionModel<PaymentModel> paymentModels = paymentModelAssembler.toCollectionModel(restaurant.getPayments())
                .removeLinks()
                .add(instantiateLinks.linkToRestaurantPayment(restaurantId))
                .add(instantiateLinks.linkToRestaurantPaymentAssociation(restaurantId, "association"));

        paymentModels.getContent().forEach(paymentModel -> {
            paymentModel.add(instantiateLinks
                    .linkToRestaurantPaymentRemoveAssociation(restaurantId, paymentModel.getId(), "removeAssociation"));
        });

        return paymentModels;
    }

    @CheckSecurity.Restaurants.AllowedToManageOperation
    @PutMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> association(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
        registrationRestaurant.associationWithPayments(restaurantId, paymentId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.AllowedToManageOperation
    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeAssociation(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
        registrationRestaurant.removeAssociationWithPayments(restaurantId, paymentId);

        return ResponseEntity.noContent().build();
    }
}
