package com.algaworks.algafoodapi.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format("User with ID %d not found", id));
    }
}