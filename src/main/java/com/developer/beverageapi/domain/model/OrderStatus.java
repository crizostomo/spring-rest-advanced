package com.developer.beverageapi.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELLED("Cancelled", CREATED);

    private String description;
    private List<OrderStatus> previousStatus;

    OrderStatus(String description, OrderStatus... previousStatus) { // OrderStatus... --> We can have nothing, one or more
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean cannotBeAlteredToANewStatus(OrderStatus newStatus) {
        return !newStatus.previousStatus.contains(this);
    }

    public boolean canBeAlteredToANewStatus(OrderStatus newStatus) {
        return !cannotBeAlteredToANewStatus(newStatus);
    }
}
