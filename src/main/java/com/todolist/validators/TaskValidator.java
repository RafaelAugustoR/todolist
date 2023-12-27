package com.todolist.validators;

import com.todolist.dtos.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class TaskValidator {
    public static List<String> validateTask(TaskDTO taskDTO) {
        List<String> errors = new ArrayList<>();
        if (taskDTO == null) {
            errors.add("Please fill the title");
            errors.add("Please fill the description");
            errors.add("Please select a category");
            return errors;
        }
        if (taskDTO.getTitle() == null || taskDTO.getTitle().isBlank()) {
            errors.add("Please fill the title");
        }
        if (taskDTO.getDescription() == null || taskDTO.getDescription().isBlank()) {
            errors.add("Please fill the description");
        }
        if (taskDTO.getCategory() == null || taskDTO.getCategory().getId() == null) {
            errors.add("Please select a category");
        }
        return errors;
    }
}
