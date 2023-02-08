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

    public boolean userAuthenticatedEqual(Long userId) {
        return getUserId() != null && userId != null
                && getUserId().equals(userId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authorityName));
    }

    public boolean allowedToManageOrders(String codeOrder) {

        return hasAuthority("SCOPE_WRITE") && (hasAuthority("MANAGE_ORDERS")
                || manageRestaurantOfOrder(codeOrder));
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    public boolean hasWriteScope() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean haReadScope() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean allowedToConsultRestaurants() {
        return hasWriteScope() && isAuthenticated();
    }

    public boolean allowedToManageRecordRestaurants() {
        return hasWriteScope() && hasAuthority("EDIT_RESTAURANTS");
    }

    public boolean allowedToManageRestaurantOperation(Long restaurantId) {
        return hasWriteScope() && (hasAuthority("EDIT_RESTAURANTS")
        || manageRestaurant(restaurantId));
    }

    public boolean allowedToConsultUsersGroupsPermissions() {
        return haReadScope() && hasAuthority("CONSULT_USERS_GROUPS_PERMISSIONS");
    }

    public boolean allowedToEditUsersGroupsPermissions() {
        return hasWriteScope() && hasAuthority("EDIT_USERS_GROUPS_PERMISSIONS");
    }

    public boolean allowedToSearchOrders(Long clientId, Long restaurantId) {
        return haReadScope() && (hasAuthority("CONSULT_ORDERS")
                || userAuthenticatedEqual(clientId)
                || manageRestaurant(restaurantId));
    }

    public boolean allowedToSearchOrders() {
        return isAuthenticated() && haReadScope();
    }

    public boolean allowedToConsultPayments() {
        return isAuthenticated() && haReadScope();
    }

    public boolean allowedToConsultCities() {
        return isAuthenticated() && haReadScope();
    }

    public boolean allowedToConsultStates() {
        return isAuthenticated() && haReadScope();
    }

    public boolean allowedToConsultKitchens() {
        return isAuthenticated() && haReadScope();
    }

    public boolean allowedToConsultStatistics() {
        return haReadScope() && hasAuthority("GENERATE_REPORTS");
    }
}
