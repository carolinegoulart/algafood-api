package com.algaworks.algafoodapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format("City with ID %d not found", id));
    }

}