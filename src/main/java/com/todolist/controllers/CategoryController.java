package com.todolist.controllers;

import com.todolist.dtos.CategoryDTO;
import com.todolist.dtos.TaskDTO;
import com.todolist.services.CategoryService;
import com.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryService categoryService;

    public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
    }

    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
    }
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<TaskDTO>> getAllTaskByCategoriesId(Long id) {
        return new ResponseEntity<>(TaskService.findByCategory(id), HttpStatus.OK);
    }

    public ResponseEntity<List<CategoryDTO>> getAllCategoriesByUserId(Long id) {
        return new ResponseEntity<>(categoryService.findAllByUserId(id), HttpStatus.OK);
    }

    public ResponseEntity<CategoryDTO> getCategory(Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    public ResponseEntity deleteCategory(Long id) {
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
