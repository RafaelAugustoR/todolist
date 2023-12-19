package com.todolist.services;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.todolist.dtos.UserDTO;
import com.todolist.entities.User;
import com.todolist.errors.ErrorCodes;
import com.todolist.errors.InvalidEntityException;
import com.todolist.errors.NotFoundException;
import com.todolist.repositories.UserRepository;
import com.todolist.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Transactional
    public UserDTO save(UserDTO user) {
        List<String> errors = userValidator.validateUser(user);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("User is not valid", (Throwable) errors);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new InvalidEntityException("Username is already in use");
        }
        String passwordHashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHashed);

        User savedUser = userRepository.save(UserDTO.toEntity(user));

        return UserDTO.fromEntity(savedUser);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        if (id == null) {
            log.error("User id is null");
            return null;
        }
        return userRepository.findById(id).map(UserDTO::fromEntity)
                .orElseThrow(() -> new NotFoundException("No user found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }
    public UserDTO login(UserDTO user) {
        List<String> errors = UserValidator.validateUserCredentials(user.getEmail(), user.getPassword());
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("User is not valid", ErrorCodes.USER_NOT_VALID, errors);
        }

        Optional<UserDTO> userByUsernameAndPassword = userRepository
                .findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(UserDTO::fromEntity);

        Optional<UserDTO> userByEmailAndPassword = userRepository
                .findUserByEmailAndPassword(user.getEmail(), user.getPassword())
                .map(UserDTO::fromEntity);

        return userByUsernameAndPassword.orElse(userByEmailAndPassword.orElseThrow(() ->
                new NotFoundException("No user found with Username = " + user.getUsername() +
                        " or Email = " + user.getEmail() + " and Password = <HIDDEN_PASSWORD>", ErrorCodes.USER_NOT_FOUND)
        ));
    }


}

