package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RepositoryRestaurantQueries {
    List<Restaurant> find(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee);
}
