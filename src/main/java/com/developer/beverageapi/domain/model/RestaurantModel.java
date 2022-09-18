package com.developer.beverageapi.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantModel {

    private String id;

    private String name;

    private BigDecimal delivery;

    private KitchenModel kitchen;
}
