package com.developer.beverageapi.infrastructure.repository.spec;

import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import javax.persistence.criteria.Predicate;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, query, builder) -> {
            if (Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("kitchen");
                root.fetch("client");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreationDateStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getCreationDateStart()));
            }

            if (filter.getCreationDateEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getCreationDateEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
