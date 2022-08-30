package com.developer.beverageapi.ExceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MESSAGE_NOT_READABLE("/message-not-readable", "This message is not readable"),
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    INVALID_ENTITY("/invalid-entity", "Violation of business rule");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://google.com.br" + path;
        this.title = title;
    }
}
