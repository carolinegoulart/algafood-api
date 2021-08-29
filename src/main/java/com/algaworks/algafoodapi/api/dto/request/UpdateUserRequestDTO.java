package com.algaworks.algafoodapi.api.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserRequestDTO {

    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

}