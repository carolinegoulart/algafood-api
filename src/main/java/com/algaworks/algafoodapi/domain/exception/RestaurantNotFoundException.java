package com.algaworks.algafoodapi.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format("Restaurant with ID %d not found", id));
    }
}