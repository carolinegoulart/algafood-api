package com.algaworks.algafoodapi.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long id) {
        this(String.format("City with ID %d not found", id));
    }

}