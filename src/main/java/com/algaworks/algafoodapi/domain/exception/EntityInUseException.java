package com.algaworks.algafoodapi.domain.exception;

public class EntityInUseException extends BusinessException {

    public EntityInUseException(String message) {
        super(message);
    }

}