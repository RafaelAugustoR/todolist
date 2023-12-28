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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
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
            throw new InvalidEntityException("User is not valid: " + String.join(", ", errors));
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new InvalidEntityException("Username is already in use");
        }
        String passwordHashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHashed);

        User savedUser = userRepository.save(UserDTO.toEntity(user));

        return UserDTO.fromEntity(savedUser);
    }
    public UserDTO updateUser(Long userId, UserDTO user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        BeanUtils.copyProperties(user, existingUser, getNullPropertyNames(user));

        User updatedUser = userRepository.save(existingUser);

        return UserDTO.fromEntity(updatedUser);
    }

    private String[] getNullPropertyNames(Object source) {
        BeanWrapper srcBeanWrapper = new BeanWrapperImpl(source);

        return Arrays.stream(srcBeanWrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> srcBeanWrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::fromEntity)
                .orElseThrow(() -> new NotFoundException("No user found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    public UserDTO login(UserDTO user) {
        List<String> errors = UserValidator.validateUserCredentials(user.getEmail(), user.getPassword());
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("User is not valid", ErrorCodes.USER_NOT_VALID, errors);
        }


        Optional<UserDTO> userByEmailAndPassword = userRepository
                .findUserByEmailAndPassword(user.getEmail(), user.getPassword())
                .map(UserDTO::fromEntity);

        return userByEmailAndPassword.orElseThrow(() ->
                new NotFoundException("No user found with Email = " + user.getEmail() + " and Password = <HIDDEN_PASSWORD>", ErrorCodes.USER_NOT_FOUND)
        );
    }


}

