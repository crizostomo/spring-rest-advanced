package com.developer.beverageapi.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message){
        super (message);
    }

    public UserNotFoundException(Long userId){
        this(String.format("There is no User with the code %d", userId));
    }
}
