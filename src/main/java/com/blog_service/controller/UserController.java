package com.blog_service.controller;

import com.blog_service.dto.UserRequestDto;
import com.blog_service.dto.UserResponseDto;
import com.blog_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users 요청을 처리하는 메소드
    @GetMapping("/users")
    public List<UserResponseDto> getAllUser() {
        return userService.getAllUser();
    }

    // POST /users 요청을 처리하는 메소드
    @PostMapping("/users")
    public ResponseEntity<Object> postUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequestDto);
    }

//    public User deleteUser(Long id, User user) {
//    }
//
//    public User patchUser(Long id, User user) {
//    }
}
