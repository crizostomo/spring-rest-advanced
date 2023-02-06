package com.developer.beverageapi.core.security;

import com.developer.beverageapi.domain.repository.RepositoryOrder;
import com.developer.beverageapi.domain.repository.RepositoryRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class Security {

    @Autowired
    private RepositoryRestaurant repositoryRestaurant;

    @Autowired
    private RepositoryOrder repositoryOrder;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            return false;
        }
        return repositoryRestaurant.existsResponsible(restaurantId, getUserId());
    }

    public boolean manageRestaurantOfOrder(String codeOrder) {
        return repositoryOrder.isOrderManagedBy(codeOrder, getUserId());
    }
}
