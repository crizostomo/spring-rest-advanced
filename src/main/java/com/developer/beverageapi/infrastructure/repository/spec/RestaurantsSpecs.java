package com.developer.beverageapi.infrastructure.repository.spec;

import com.developer.beverageapi.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantsSpecs {

    public static Specification<Restaurant> withFreeDelivery(){
        return (root, query, builder) ->
                builder.equal(root.get("delivery"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withSimilarName(String name){
        return (root, query, builder) ->
            builder.like(root.get("name"), "%" + name + "%");
    }
}
