package com.todolist.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    private List<CategoryDTO> category;

    public static User toEntity(UserDTO userDTO) {
        final User user = new User();
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCategory(
                userDTO.getCategory() != null ? userDTO.getCategory().stream().map(CategoryDTO::toEntity).collect(Collectors.toList()) : null
        );

        return user;
    }

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .category(
                        user.getCategory() != null ? user.getCategory().stream().map(CategoryDTO::fromEntity).collect(Collectors.toList()) : null
                )
                .build();
    }



}
