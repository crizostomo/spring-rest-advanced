package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RepositoryRestaurant extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByDeliveryBetween (BigDecimal initialFee, BigDecimal finalFee);

    List<Restaurant> findByNameContainingAndKitchenId (String name, Long kitchen);
}
