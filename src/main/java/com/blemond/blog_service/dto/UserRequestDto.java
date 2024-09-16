package com.blemond.blog_service.dto;

import com.blemond.blog_service.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequestDto {

    private String username;
    private String email;
    private String passwordHash;

    @Builder
    public UserRequestDto(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
