package com.developer.beverageapi.ExceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    FIELD_NOT_ALLOWED("/field-not-allowed", "This field is not allowed"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
    MESSAGE_NOT_READABLE("/message-not-readable", "This message is not readable"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://google.com.br" + path;
        this.title = title;
    }
}
