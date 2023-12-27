package com.todolist.repositories;

import com.todolist.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoryByUserId(Long userId);

    @Query("select t.category.id from tb_tasks t where t.id = :taskId")
    Long findCategoryByTaskId(@Param("taskId") Long taskId);
}