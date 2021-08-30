package com.algaworks.algafoodapi.domain.exception;

public class PaymentOptionNotFoundException extends EntityNotFoundException {

    public PaymentOptionNotFoundException(String message) {
        super(message);
    }

    public PaymentOptionNotFoundException(Long id) {
        this(String.format("Payment option with ID %d not found", id));
    }

}