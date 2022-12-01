package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.PaymentModelAssembler;
import com.developer.beverageapi.api.model.PaymentModel;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantPayment {

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private PaymentModelAssembler paymentModelAssembler;

        @GetMapping
    public List<PaymentModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        return paymentModelAssembler.toCollectionModel(restaurant.getPayments());
    }

    @PutMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void association(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
            registrationRestaurant.associationWithPayments(restaurantId, paymentId);
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssociation(@PathVariable Long restaurantId, @PathVariable Long paymentId) {
            registrationRestaurant.removeAssociationWithPayments(restaurantId, paymentId);
    }
}
