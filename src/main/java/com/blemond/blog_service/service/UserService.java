package com.blemond.blog_service.service;

import com.blemond.blog_service.entity.UserEntity;
import com.blemond.blog_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getUser() {
        return userRepository.findAll();
    }
}
