package com.developer.beverageapi.api;

import com.developer.beverageapi.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class InstantiateLinks {

    public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
            new TemplateVariable("projection", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToOrders(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("creationDateStart", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("creationDateEnd", TemplateVariable.VariableType.REQUEST_PARAM));

        String ordersUrl = WebMvcLinkBuilder.linkTo(ControllerOrder.class).toUri().toString();

        return Link.of(UriTemplate.of(ordersUrl, PAGINATION_VARIABLES.concat(filterVariables)), rel);
    }

    public Link linkToRestaurantOpening(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class).open(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantClosing(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class).close(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantInactive(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class).inactive(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantActive(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class).active(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurants(String rel) {
        String restaurantsUrl = WebMvcLinkBuilder.linkTo(ControllerRestaurant.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantsUrl, PROJECTION_VARIABLES), rel);
    }

    public Link linkToGroups(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerGroup.class).withRel(rel);
    }

    public Link linkToGroups() {
        return linkToGroups(IanaLinkRelations.SELF.value());
    }

    public Link linkToPermissionsGroup(Long groupId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerGroupPermissions.class).list(groupId)).withRel(rel);
    }

    public Link linkToRestaurants() {
        return linkToRestaurants(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPayment(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantPayment.class).list(restaurantId))
                .withRel(rel);
    }

    public Link linkToRestaurantPayment(Long restaurantId) {
        return linkToRestaurantPayment(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPaymentRemoveAssociation(Long restaurantId, Long paymentId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantPayment.class)
                .removeAssociation(restaurantId, paymentId)).withRel(rel);
    }

    public Link linkToRestaurantPaymentAssociation(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantPayment.class)
                .association(restaurantId, null)).withRel(rel);
    }

    public Link linkToRestaurantResponsibleRemoveAssociation(Long restaurantId, Long userId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantUserResponsible.class)
                .removeAssociation(restaurantId, userId)).withRel(rel);
    }

    public Link linkToRestaurantResponsibleAssociation(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantPayment.class)
                .association(restaurantId, null)).withRel(rel);
    }

    public Link linkToProductPhoto(Long restaurantId, Long productId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantProduct.class)
                .search(restaurantId, productId)).withRel(rel);
    }

    public Link linkToProductPhoto(Long restaurantId, Long productId) {
        return linkToProductPhoto(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchen(Long kitchenId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerKitchen.class).search(kitchenId)).withRel(rel);
    }

    public Link linkToKitchen(Long kitchenId) {
        return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProducts(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantProduct.class).list(restaurantId, null))
                .withRel(rel);
    }

    public Link linkToProducts(Long restaurantId) {
        return linkToProducts(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToOrderConfirmation(String orderCode, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerOrderStatus.class).confirm(orderCode)).withRel(rel);
    }

    public Link linkToOrderCancellation(String orderCode, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerOrderStatus.class).cancel(orderCode)).withRel(rel);
    }

    public Link linkToOrderDelivery(String orderCode, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerOrderStatus.class).delivery(orderCode)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurant.class)
                .search(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId) {
        return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUser(Long userId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerUser.class)
                .search(userId)).withRel(rel);
    }

    public Link linkToUser(Long userId) {
        return linkToUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsers(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerUser.class).withRel(rel);
    }

    public Link linkToUsers() {
        return linkToUsers(IanaLinkRelations.SELF.value());
    }

    public Link linkToUserGroups(Long userId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerUserGroup.class)
                .list(userId)).withRel(rel);
    }

    public Link linkToUserGroups(Long userId) {
        return linkToUserGroups(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantResponsible(Long restaurantId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantUserResponsible.class)
                .list(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantResponsible(Long restaurantId) {
        return linkToRestaurantResponsible(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPayment(Long paymentId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerPayment.class)
                .search(paymentId, null)).withRel(rel);
    }

    public Link linkToPayment(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerPayment.class).withRel(rel);
    }

    public Link linkToPayment() {
        return linkToPayment(IanaLinkRelations.SELF.value());
    }

    public Link linkToPayment(Long paymentId) {
        return linkToPayment(paymentId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCity(Long cityId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerCity.class)
                .search(cityId)).withRel(rel);
    }

    public Link linkToCity(Long cityId) {
        return linkToCity(cityId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCities(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerCity.class).withRel(rel);
    }

    public Link linkToCities() {
        return linkToCities(IanaLinkRelations.SELF.value());
    }

    public Link linkToState(Long stateId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerState.class)
                .search(stateId)).withRel(rel);
    }

    public Link linkToState(Long stateId) {
        return linkToState(stateId, IanaLinkRelations.SELF.value());
    }

    public Link linkToStates(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerState.class).withRel(rel);
    }

    public Link linkToStates() {
        return linkToStates(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduct(Long restaurantId, Long productId, String rel) {
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ControllerRestaurantProduct.class)
                        .search(restaurantId, productId))
                .withRel(rel);
    }

    public Link linkToProduct(Long restaurantId, Long productId) {
        return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchens(String rel) {
        return WebMvcLinkBuilder.linkTo(ControllerKitchen.class).withRel(rel);
    }

    public Link linkToKitchens() {
        return linkToKitchens(IanaLinkRelations.SELF.value());
    }
}
