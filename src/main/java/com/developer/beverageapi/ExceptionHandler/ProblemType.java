package com.developer.beverageapi.ExceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    FIELD_NOT_ALLOWED("/field-not-allowed", "This field is not allowed"),
    INVALID_ENTITY("/invalid-entity", "Violation of business rule"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
    MESSAGE_NOT_READABLE("/message-not-readable", "This message is not readable");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://google.com.br" + path;
        this.title = title;
    }
}
