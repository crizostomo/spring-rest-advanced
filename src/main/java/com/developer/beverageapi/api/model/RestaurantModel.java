package com.developer.beverageapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {

    private Long id;
    private String name;
    private BigDecimal delivery;
    private KitchenModel kitchen;
    private Boolean active;
    private AddressModel address;
}
