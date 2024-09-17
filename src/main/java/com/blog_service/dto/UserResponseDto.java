package com.blog_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}
