package com.todolist.dtos;

import com.todolist.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String title;

    private String description;
    private String priority;

    private LocalDateTime startData;
    private LocalDateTime endData;

    private LocalDateTime createdData;

    private LocalDateTime updatedAt;

    private CategoryDTO category;

    public static Task toEntity(TaskDTO taskDTO) throws Exception {
        final Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStartData(taskDTO.getStartData());
        task.setEndData(taskDTO.getEndData());
        task.setCreatedData(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setCategory(CategoryDTO.toEntity(taskDTO.getCategory()));

        return task;
    }

    public static TaskDTO fromEntity(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .startData(task.getStartData())
                .endData(task.getEndData())
                .createdData(task.getCreatedData())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
