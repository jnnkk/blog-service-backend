package com.blog_service.controller;

import com.blog_service.dto.UserDto;
import com.blog_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users 요청을 처리하는 메소드
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    // GET /users/{userId} 요청을 처리하는 메소드
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getUserByUserId(@Valid @PathVariable String userId) {
        UserDto.UserResponseDto userResponseDto = userService.getUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    // POST /users 요청을 처리하는 메소드
    @PostMapping("/users")
    public ResponseEntity<Object> postUser(@Valid @RequestBody UserDto.UserGetRequestDto userGetRequestDto) {
        userService.createUser(userGetRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userGetRequestDto);
    }

    // PATCH /users/{userId} 요청을 처리하는 메소드
    @PatchMapping("/users/{userId}")
    public ResponseEntity<Object> patchUser(@PathVariable String userId,
                                            @Valid @RequestBody UserDto.UserPatchRequestDto userPatchRequestDto) {
        UserDto.UserResponseDto userResponseDto = userService.updateUser(userId, userPatchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
