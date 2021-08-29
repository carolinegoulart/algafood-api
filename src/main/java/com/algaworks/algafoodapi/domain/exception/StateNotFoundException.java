package com.algaworks.algafoodapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long id) {
        this(String.format("State with ID %d not found", id));
    }
}