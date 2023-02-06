package com.developer.beverageapi.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Kitchens {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_KITCHENS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToManageRecord {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }

    public @interface Restaurants {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToManageRecord {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDIT_RESTAURANTS') or " +
                "@security.manageRestaurant(#restaurantId))")
        // #restaurantId --> get access to the restaurantId in the controller
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToManageOperation {
        }
    }

    public @interface Orders {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULT_ORDERS') or " +
                "@security.getUserId() == returnObject.client.id or " +
                "@security.manageRestaurant(returnObject.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToGet {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULT_ORDERS') or " +
                "@security.getUserId() == #filter.clientId or " +
                "@security.manageRestaurant(#filter.restaurant.id))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToSearch {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToCreate {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('MANAGE_ORDERS') or " +
                "@security.manageRestaurantOfOrder(#codeOrder))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToManageOrders {
        }
    }

    public @interface Payment {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PAYMENTS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToEdit {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }
}
