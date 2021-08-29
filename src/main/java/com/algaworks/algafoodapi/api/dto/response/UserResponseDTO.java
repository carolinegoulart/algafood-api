package com.algaworks.algafoodapi.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDTO {

    private final Long id;
    private final String name;
    private final String email;

}