package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.assembler.PaymentModelAssembler;
import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantPayment {

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @GetMapping
    public CollectionModel<PaymentModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        CollectionModel<PaymentModel> paymentModels = paymentModelAssembler.toCollectionModel(restaurant.getPayments())
                .removeLinks()
                .add(instantiateLinks.linkToRestaurantPayment(restaurantId));

        paymentModels.getContent().forEach(paymentModel -> {
            paymentModel.add(instantiateLinks
                    .linkToRestaurantPaymentRemoveAssociation(restaurantId, paymentModel.getId(), "removeAssociation"));
        });

        return paymentModels;
    }

    @PutMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void association(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
        registrationRestaurant.associationWithPayments(restaurantId, paymentId);
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeAssociation(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
        registrationRestaurant.removeAssociationWithPayments(restaurantId, paymentId);

        return ResponseEntity.noContent().build();
    }
}
