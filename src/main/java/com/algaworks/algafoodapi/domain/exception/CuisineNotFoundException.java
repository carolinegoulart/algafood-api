package com.algaworks.algafoodapi.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {

    public CuisineNotFoundException(String message) {
        super(message);
    }

    public CuisineNotFoundException(Long id) {
        this(String.format("Cuisine with ID %d not found", id));
    }
}