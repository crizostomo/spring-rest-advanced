package com.developer.beverageapi.infrastructure.repository.spec;

import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.repository.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.function.Predicate;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();
            root.fetch("restaurant").fetch("kitchen");
            root.fetch("client");

            if (filter.getClientId() != null) {
                predicates.add((Predicate) builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add((Predicate) builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreationDateStart() != null) {
                predicates.add((Predicate) builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getCreationDateStart()));
            }

            if (filter.getCreationDateEnd() != null) {
                predicates.add((Predicate) builder.lessThanOrEqualTo(root.get("creationDate"), filter.getCreationDateEnd()));
            }

            return builder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
        };
    }

}
