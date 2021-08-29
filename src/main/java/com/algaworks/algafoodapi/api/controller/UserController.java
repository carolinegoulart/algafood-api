package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.api.dto.request.CreateUserRequestDTO;
import com.algaworks.algafoodapi.api.dto.request.UpdateUserPasswordRequestDTO;
import com.algaworks.algafoodapi.api.dto.request.UpdateUserRequestDTO;
import com.algaworks.algafoodapi.api.dto.response.UserResponseDTO;
import com.algaworks.algafoodapi.domain.model.User;
import com.algaworks.algafoodapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll().stream()
                .map(User::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserResponseDTO findById(@PathVariable Long userId) {
        return userService.findById(userId).toUserResponseDTO();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody CreateUserRequestDTO userRequest) {
        return userService.create(userRequest).toUserResponseDTO();
    }

    @PutMapping("/{userId}")
    public UserResponseDTO update(@PathVariable Long userId,
                                  @Valid @RequestBody UpdateUserRequestDTO userRequest) {
        return userService.update(userId, userRequest).toUserResponseDTO();
    }

    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long userId,
                               @Valid @RequestBody UpdateUserPasswordRequestDTO userRequest) {
        userService.updatePassword(userId, userRequest).toUserResponseDTO();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }

}