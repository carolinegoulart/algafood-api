package com.algaworks.algafoodapi.api.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserPasswordRequestDTO {

    @NotBlank
    private final String currentPassword;

    @NotBlank
    private final String newPassword;

}