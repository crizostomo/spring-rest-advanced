package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryOrder extends CustomJpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

//    @Query("from Order where code = :code") --> We don't need this because we have already in the class 'Order'
    Optional<Order> findByCode(String code);

    @Query("from Order p join fetch p.client join fetch p.restaurant r join fetch r.kitchen")
    List<Order> findAll();

    boolean isOrderManagedBy(String codeOrder, Long userId);
}
