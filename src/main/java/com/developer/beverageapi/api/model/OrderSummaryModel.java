package com.developer.beverageapi.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

//@JsonFilter("orderFilter")
@Getter
@Setter
public class OrderSummaryModel {

    private String code;
    private BigDecimal subtotal;
    private BigDecimal delivery;
    private BigDecimal total;
    private String status;
    private OffsetDateTime creationDate;
    private RestaurantSummaryModel restaurant;
    private UserModel client;
}