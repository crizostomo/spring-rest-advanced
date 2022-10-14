package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryOrder extends CustomJpaRepository<Order, Long> {

    @Query("from Order p join fetch p.client join fetch p.restaurant r join fetch r.kitchen")
    List<Order> findAll();
}
