package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryRestaurant extends CustomJpaRepository<Restaurant, Long>, RepositoryRestaurantQueries,
        JpaSpecificationExecutor<Restaurant> {

    /**
     * This annotation helps to diminish the quantity of selects
     * We used JOIN fetch r.payments to decrease the quantity
     * of selects as well. In this case, we had to use fetch
     * because of the association ManyToMany. If a restaurant
     * doesn't have any payments associated to it, no restaurant
     * will be returned, to solve this we have to use LEFT JOIN
     * FETCH instead of JOIN FETCH.
     * In ManyToOne associations the fetch is created automatically
     * In this case "from Restaurant r join fetch" the fetch isn't
     * necessary, we just added to make it explicit.
     */
    @Query("from Restaurant r join fetch r.kitchen join fetch r.payments")
    List<Restaurant> findAll();

    List<Restaurant> findByDeliveryBetween(BigDecimal initialFee, BigDecimal finalFee);

    //"read, get, query, stream" instead of find

    // List<Restaurant> findByNameContainingAndKitchenId (String name, Long kitchen);

    // @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> findByName(String name, @Param("id") Long kitchen);

    Optional<Restaurant> findFirstByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchen);

//    @Query("select case when count(1) > 0 then true else false end from Restaurant rest join rest.responsible resp \n" +
//            "where rest.id = :restaurantId and resp.id = :userId")
    boolean existsResponsible(Long restaurantId, Long userId);
}
