package com.todolist.services;

import com.todolist.dtos.CategoryDTO;
import com.todolist.errors.ErrorCodes;
import com.todolist.errors.InvalidEntityException;
import com.todolist.errors.NotFoundException;
import com.todolist.repositories.CategoryRepository;
import com.todolist.validators.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO save(CategoryDTO category) {
        List<String> errors = CategoryValidator.validateCategory(category);
        if (!errors.isEmpty()) {
            log.error("Category is not valid {}", category);
            throw new InvalidEntityException("Category is not valid", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDTO.fromEntity(categoryRepository.save(CategoryDTO.toEntity(category)));
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        if (id == null) {
            log.error("Category id is null");
            return null;
        }
        return categoryRepository.findById(id).map(CategoryDTO::fromEntity)
                .orElseThrow(() -> new NotFoundException("No Category found with ID = " + id, ErrorCodes.USER_NOT_FOUND));
    }

    public List<CategoryDTO> findAllByUserId(Long userId) {
        return categoryRepository.findCategoryByUserId(userId).stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (id == null) {
            log.error("Category id is null");
            return;
        }
        categoryRepository.deleteById(id);
    }
}
