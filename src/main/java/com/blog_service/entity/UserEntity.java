package com.blog_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 매핑
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public UserEntity(String userId, String password, String username, String email, LocalDateTime createdAt) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }

    // 필드 변경을 위한 메서드, 유효성 검사를 추가할 수 있음
    public void changeUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    public void changeEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public void changePassword(String password) {
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        this.password = password;
    }
}
