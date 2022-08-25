package com.developer.beverageapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not Found")
public class EntityNotFoundException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public EntityNotFoundException(String message){
        this (HttpStatus.NOT_FOUND, message);
    }
}
