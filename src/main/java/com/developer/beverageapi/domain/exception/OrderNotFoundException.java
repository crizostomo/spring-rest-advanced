package com.developer.beverageapi.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String codeOrder){
        super(String.format("There is no Order with the code %d", codeOrder));
    }
}
