package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RestaurantRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialDeliveryFee, BigDecimal finalDeliveryFee) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteria.from(Restaurant.class);// from Restaurant

        Predicate predicateName = builder.like(root.get("name"), "%" + name + "%");

        Predicate initialFeePredicate = builder
                .greaterThanOrEqualTo(root.get("delivery"), initialDeliveryFee);

        Predicate finalFeePredicate = builder
                .lessThanOrEqualTo(root.get("delivery"), finalDeliveryFee);

        criteria.where(predicateName, initialFeePredicate, finalFeePredicate);

        TypedQuery<Restaurant> query = manager.createQuery(criteria);
        return query.getResultList();
    }
}
