package com.todolist.controllers;

import com.todolist.dtos.CategoryDTO;
import com.todolist.dtos.TaskDTO;
import com.todolist.services.CategoryService;
import com.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.save(categoryDTO), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<TaskDTO>> getAllTaskByCategoriesId(Long id) {
        return new ResponseEntity<>(TaskService.findByCategory(id), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesByUserId(Long id) {
        return new ResponseEntity<>(categoryService.findAllByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(Long id) {
        categoryService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
