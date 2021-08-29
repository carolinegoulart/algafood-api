package com.algaworks.algafoodapi.domain.exception;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }

    public PasswordsDoNotMatchException() {
        this("Invalid current password.");
    }

}