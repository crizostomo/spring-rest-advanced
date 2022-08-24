package com.developer.beverageapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not Found")
public class EntityNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message){
        super(message);
    }
}
