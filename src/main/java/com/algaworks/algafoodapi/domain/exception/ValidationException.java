package com.algaworks.algafoodapi.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {

    private final BindingResult bindingResult;

}