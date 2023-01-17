package com.developer.beverageapi.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderInput {

    @Valid
    @NotNull
    private RestaurantIDInput restaurant;

//    @Valid
//    @NotNull
    private AddressInput address;

    @Valid
    @NotNull
    private PaymentIDInput payment;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<OrderItemInput> items;
}
