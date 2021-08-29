package com.algaworks.algafoodapi.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(Long id) {
        this(String.format("Group with ID %d not found", id));
    }

}