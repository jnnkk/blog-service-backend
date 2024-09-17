package com.blog_service.service;

import com.blog_service.dto.UserRequestDto;
import com.blog_service.dto.UserResponseDto;
import com.blog_service.entity.UserEntity;
import com.blog_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .toList();
    }

    public void createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(userEntity);
    }
}
