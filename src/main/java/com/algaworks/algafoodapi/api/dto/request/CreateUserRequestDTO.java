package com.algaworks.algafoodapi.api.dto.request;

import com.algaworks.algafoodapi.domain.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String password;

    public User toUser() {
        return User.builder()
                .name(this.getName())
                .email(this.getEmail())
                .password(this.getPassword())
                .build();
    }

}