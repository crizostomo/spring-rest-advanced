package com.developer.beverageapi.domain.exception;

public class PhotoNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public PhotoNotFoundException(String message) {
        super(message);
    }

    public PhotoNotFoundException(Long restaurantId, Long productId) {
        this(String.format("There is no record of photo of product with the code %d for the restaurant with the" +
                "code %d", productId, restaurantId));
    }
}
