package com.developer.beverageapi.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String message){
        super (message);
    }

    public OrderNotFoundException(Long orderId){
        this(String.format("There is no Order with the code %d", orderId));
    }
}
