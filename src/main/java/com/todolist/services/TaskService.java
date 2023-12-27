package com.todolist.services;

import com.todolist.dtos.CategoryDTO;
import com.todolist.dtos.TaskDTO;
import com.todolist.entities.Category;
import com.todolist.entities.Task;
import com.todolist.errors.ErrorCodes;
import com.todolist.errors.InvalidEntityException;
import com.todolist.errors.NotFoundException;
import com.todolist.repositories.CategoryRepository;
import com.todolist.repositories.TaskRepository;
import com.todolist.validators.TaskValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private static TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public TaskDTO save(TaskDTO taskDTO) throws Exception {
        List<String> errors = TaskValidator.validateTask(taskDTO);
        if (!errors.isEmpty()) {
            log.error("Task is not valid {}", taskDTO);
            throw new InvalidEntityException("Task is not valid", ErrorCodes.TASK_NOT_VALID, errors);
        }

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskDTO.getStartData()) || currentDate.isAfter(taskDTO.getEndData())) {
            throw new InvalidEntityException("Invalid start/end date.", ErrorCodes.INVALID_DATE, List.of("Start/End date cannot be in the past."));
        }

        if (taskDTO.getStartData().isAfter(taskDTO.getEndData())) {
            throw new InvalidEntityException("Invalid start/end date.", ErrorCodes.INVALID_DATE, List.of("Start date cannot be after End date."));
        }

        Task savedTask = taskRepository.save(TaskDTO.toEntity(taskDTO));
        return TaskDTO.fromEntity(savedTask);
    }

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public TaskDTO findById(Long id) {
        if (id == null) {
            log.error("User id is null");
            return null;
        }
        final Long categoryId = categoryRepository.findCategoryByTaskId(id);
        Category category = new Category();
        category.setId(categoryId);

        final Optional<Task> task = taskRepository.findById(id);
        task.ifPresent(value -> value.setCategory(category));

        final TaskDTO taskDTO = TaskDTO.fromEntity(task.get());
        CategoryDTO categoryDto = CategoryDTO.fromEntity(category);
        taskDTO.setCategory(categoryDto);

        return Optional.of(taskDTO).
                orElseThrow(() -> new NotFoundException("No Task found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    public static List<TaskDTO> findByCategory(Long categoryId) {
        return taskRepository.findTaskByCategoryId(categoryId).stream()
                .map(TaskDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean delete(Long id) {
        if (id == null) {
            log.error("Task id is null");
            return false;
        }
        taskRepository.deleteById(id);
        return false;
    }
}
