package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Product;
import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryProduct extends JpaRepository<Product, Long>, RepositoryProductQueries {

    @Query("from Product where restaurant.id = :restaurant and id = :product")
    Optional<Product> findById(@Param("restaurant") Long restaurantId,
                               @Param("product") Long productId);

    List<Product> findProductsByRestaurant(Restaurant restaurant);

    @Query("from Product p where p.active = true and p.restaurant = :restaurant")
    List<Product> findActivesByRestaurant(Restaurant restaurant);

    // Inside ProductPhoto we do not have a RESTAURANT, but we have it inside PRODUCT. Therefore, it was made a JOIN using
    // f.product p where p.restaurant.id = :restaurantId | the f represents the restaurant
    @Query("select f from ProductPhoto f join f.product p " +
            "where p.restaurant.id = :restaurantId and f.product.id =:productId")
    Optional<ProductPhoto> findPhotoById(Long restaurantId, Long productId);
}
