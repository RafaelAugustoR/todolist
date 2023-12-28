package com.todolist.validators;

import com.todolist.dtos.UserDTO;
import com.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {


    private final UserRepository userRepository;
    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> validateUser(UserDTO user) {
        List<String> errors = new ArrayList<>();
        if (user == null) {
            errors.add("Please fill the name");
            errors.add("Please fill the Username");
            errors.add("Please fill the user Email");
            errors.add("Please fill the user Password");
        } else {
            if (StringUtils.isEmpty(user.getName()))
                errors.add("Please fill the name");

            if (StringUtils.isEmpty(user.getUsername()))
                errors.add("Please fill the Username");
            else if (userRepository.existsByUsername(user.getUsername()))
                errors.add("Username is already in use");

            if (StringUtils.isEmpty(user.getEmail()))
                errors.add("Please fill the Email");
            else if (userRepository.existsByEmail(user.getEmail()))
                errors.add("Email is already in use");

            if (StringUtils.isEmpty(user.getPassword()))
                errors.add("Please fill the Password");
        }
        return errors;
    }

    public static List<String> validateUserCredentials(String email, String password) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(email)) {
            errors.add("Email is empty");
        }
        if (StringUtils.isEmpty(password)) {
            errors.add("Password is empty");
        }
        return errors;
    }
}
