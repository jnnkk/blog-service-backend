package com.blemond.blog_service.controller;

import com.blemond.blog_service.entity.UserEntity;
import com.blemond.blog_service.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUser() {
        return userService.getUser();
    }

//    public User getUserById(Long id) {
//    }
//
//    public User postUser(User user) {
//    }
//
//    public User deleteUser(Long id, User user) {
//    }
//
//    public User patchUser(Long id, User user) {
//    }
}
