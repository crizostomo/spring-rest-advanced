package com.developer.beverageapi.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public GroupNotFoundException(String message){
        super (message);
    }

    public GroupNotFoundException(Long groupId){
        this(String.format("There is no Group with the code %d", groupId));
    }
}
