package com.blemond.blog_service.controller;

import com.blemond.blog_service.dto.UserRequestDto;
import com.blemond.blog_service.dto.UserResponseDto;
import com.blemond.blog_service.service.UserService;
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
    public UserRequestDto postUser(@RequestBody UserRequestDto userRequestDto) {
        userService.saveUser(userRequestDto);
        return userRequestDto;
    }

//    public User deleteUser(Long id, User user) {
//    }
//
//    public User patchUser(Long id, User user) {
//    }
}
