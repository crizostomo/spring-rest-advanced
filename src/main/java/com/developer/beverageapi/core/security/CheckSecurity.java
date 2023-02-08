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

        @PreAuthorize("@security.allowedToConsultKitchens()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }

    public @interface Restaurants {
        @PreAuthorize("@security.allowedToManageRecordRestaurants()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToManageRecord {
        }

        @PreAuthorize("@security.allowedToConsultRestaurants()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }

//        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
//                "(hasAuthority('EDIT_RESTAURANTS') or " +
//                "@security.manageRestaurant(#restaurantId))")
        @PreAuthorize("@security.allowedToManageRestaurantOperation(#restaurantId)")
        // #restaurantId --> get access to the restaurantId in the controller
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToManageOperation {
        }
    }

    public @interface Orders {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULT_ORDERS') or " +
                "@security.userAuthenticatedEqual(returnObject.client.id) or " +
                "@security.manageRestaurant(returnObject.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToGet {
        }

        @PreAuthorize("@security.allowedToSearchOrders(#filter.clientId, #filter.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToSearch {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToCreate {
        }

        @PreAuthorize("@security.allowedToManageOrders(#codeOrder)")
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

//        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PreAuthorize("@security.allowedToConsultPayments()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }

    public @interface Cities {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITIES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToEdit {
        }

        @PreAuthorize("@security.allowedToConsultCities()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }

    public @interface States {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_STATES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToEdit {
        }

        @PreAuthorize("@security.allowedToConsultStates()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }

    public @interface UsersGroupsPermissions {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @security.userAuthenticatedEqual(#userId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToChangeOwnPassword {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USERS_GROUPS_PERMISSIONS') or " +
                "@security.userAuthenticatedEqual(#userId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToChangeUser {
        }

//        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_USERS_GROUPS_PERMISSIONS')")
        @PreAuthorize("@security.allowedToEditUsersGroupsPermissions()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToEdit {
        }

        @PreAuthorize("@security.allowedToConsultUsersGroupsPermissions()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }

    public @interface Statistics {
        @PreAuthorize("@security.allowedToConsultStatistics()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface AllowedToConsult {
        }
    }
}
