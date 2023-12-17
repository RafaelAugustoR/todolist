package com.todolist.dtos;


import com.todolist.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private UserDTO user;

    private List<TaskDTO> taskList;

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setUser(UserDTO.toEntity(categoryDTO.getUser()));
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        return category;
    }
    public static CategoryDTO fromEntity(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .taskList(
                        category.getTaskList() != null ? category.getTaskList().stream().map(TaskDTO::fromEntity).collect(Collectors.toList()) : null
                )
                .build();
    }


}
