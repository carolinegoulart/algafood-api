package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.api.dto.request.CreateUserRequestDTO;
import com.algaworks.algafoodapi.api.dto.request.UpdateUserPasswordRequestDTO;
import com.algaworks.algafoodapi.api.dto.request.UpdateUserRequestDTO;
import com.algaworks.algafoodapi.domain.exception.BusinessException;
import com.algaworks.algafoodapi.domain.exception.UserNotFoundException;
import com.algaworks.algafoodapi.domain.model.User;
import com.algaworks.algafoodapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(CreateUserRequestDTO userRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());

        if (existingUser.isPresent()) {
            throw new BusinessException("Email is already in use");
        }

        User user = userRequest.toUser();
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User update(Long userId, UpdateUserRequestDTO userRequest) {
        User userFromDB = findById(userId);
        Optional<User> existingUserByEmail = userRepository.findByEmail(userRequest.getEmail());

        if (existingUserByEmail.isPresent() && !existingUserByEmail.get().getId().equals(userId)) {
            throw new BusinessException("Email is already in use");
        }

        userFromDB.updateUserData(userRequest);
        return userRepository.save(userFromDB);
    }

    public User updatePassword(Long userId, UpdateUserPasswordRequestDTO userRequest) {
        User userFromDB = findById(userId);

        if (!userFromDB.getPassword().equals(userRequest.getCurrentPassword())) {
            throw new BusinessException("Invalid current password");
        }

        userFromDB.updateUserPassword(userRequest.getNewPassword());
        return userRepository.save(userFromDB);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }

}