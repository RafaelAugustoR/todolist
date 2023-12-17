package com.todolist.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity (name = "tb_tasks")
public class Task {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUser;
    private String description;

    @Column(length = 50)
    private String title;
    private String priority;

    private LocalDateTime startData;
    private LocalDateTime endData;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdData;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("The title field must contain a maximum of 50 characters.");
        }

        this.title = title;
    }

}