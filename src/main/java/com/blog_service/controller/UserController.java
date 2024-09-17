package com.blog_service.controller;

import com.blog_service.dto.UserRequestDto;
import com.blog_service.dto.UserResponseDto;
import com.blog_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponseDto> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/users")
    public ResponseEntity<String> postUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return ResponseEntity.ok("User created successfully");
    }

//    public User deleteUser(Long id, User user) {
//    }
//
//    public User patchUser(Long id, User user) {
//    }
}
