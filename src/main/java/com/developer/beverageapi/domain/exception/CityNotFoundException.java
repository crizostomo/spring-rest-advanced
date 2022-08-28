package com.developer.beverageapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message){
        super (message);
    }

    public CityNotFoundException(Long cityId){
        this(String.format("There is no City with the code %d", cityId));
    }
}
