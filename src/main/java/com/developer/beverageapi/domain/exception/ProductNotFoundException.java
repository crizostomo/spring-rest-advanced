package com.developer.beverageapi.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message){
        super (message);
    }

    public ProductNotFoundException(Long restaurantId, Long productId){
        this(String.format("There is no record of the product with the code %d for the restaurant of code %d", productId, restaurantId));
    }
    public ProductNotFoundException(Long productId){
        this(String.format("There is no product with the code %d", productId));
    }
}
