package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RepositoryRestaurant extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries {

    List<Restaurant> findByDeliveryBetween (BigDecimal initialFee, BigDecimal finalFee);

    //"read, get, query, stream" instead of find

    // List<Restaurant> findByNameContainingAndKitchenId (String name, Long kitchen);

//    @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> findByName (String name, @Param("id") Long kitchen);

    Optional<Restaurant> findFirstByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchen);

}
