package com.developer.beverageapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message){
        super (message);
    }

    public StateNotFoundException(Long stateId){
        this(String.format("There is no State with the code %d", stateId));
    }
}
