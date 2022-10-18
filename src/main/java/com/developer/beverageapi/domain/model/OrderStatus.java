package com.developer.beverageapi.domain.model;

public enum OrderStatus {

    CREATED("Created"),
    CONFIRMED("Confirmed"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
