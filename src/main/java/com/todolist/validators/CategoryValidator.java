package com.todolist.validators;

import com.todolist.dtos.CategoryDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {
    public static List<String> validateCategory(CategoryDTO categoryDTO) {
        List<String> errors = new ArrayList<>();
        if (categoryDTO == null) {
            errors.add("Fill the name");
            errors.add("Fill the description");
            return  errors;
        }
        if (StringUtils.isEmpty(categoryDTO.getName())) {
            errors.add("Please fill the name");
        }
        if (StringUtils.isEmpty(categoryDTO.getDescription())) {
            errors.add("Please fill the description");
        }
        return errors;
    }
}
