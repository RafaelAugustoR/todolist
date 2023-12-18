package com.todolist.validators;

import com.todolist.dtos.UserDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static List<String> validateUser(UserDTO user) {
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

            if (StringUtils.isEmpty(user.getEmail()))
                errors.add("Please fill the Email");

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
