package com.developer.beverageapi.domain.model.mixin;

import com.developer.beverageapi.domain.model.Address;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.model.Payment;
import com.developer.beverageapi.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMixin {

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "name"}, allowGetters = true)
    private Kitchen kitchen;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private LocalDateTime recordDate;

    @JsonIgnore
    private LocalDateTime updatedDate;

    //    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @JsonIgnore
    private List<Product> products = new ArrayList<>();

}
