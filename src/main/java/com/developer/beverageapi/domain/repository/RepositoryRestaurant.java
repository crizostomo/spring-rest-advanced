package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RepositoryRestaurant extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByDeliveryBetween (BigDecimal initialFee, BigDecimal finalFee);

    //"read, get, query, stream" instead of find

    List<Restaurant> findByNameContainingAndKitchenId (String name, Long kitchen);

    Optional<Restaurant> findFirstByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchen);
}
