package com.blog_service.service;

import com.blog_service.dto.UserDto;
import com.blog_service.entity.UserEntity;
import com.blog_service.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    // 모든 사용자 정보를 조회하는 메소드
    public List<UserDto.UserResponseDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.UserResponseDto.class)) // UserEntity -> UserResponseDto로 변환
                .toList();
    }

    // 사용자 정보를 생성하는 메소드
    public void createUser(UserDto.UserGetRequestDto userGetRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .userId(userGetRequestDto.getUserId())
                .username(userGetRequestDto.getUsername())
                .email(userGetRequestDto.getEmail())
                .password(userGetRequestDto.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(userEntity);
    }

    // 사용자 ID로 사용자 정보를 조회하는 메소드
    public UserDto.UserResponseDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return modelMapper.map(userEntity, UserDto.UserResponseDto.class);
    }

    // 사용자 정보를 수정하는 메소드
    @Transactional
    public UserDto.UserResponseDto updateUser(String userId, UserDto.UserPatchRequestDto userPatchRequestDto) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // UserPatchRequestDto의 값이 null이 아닌 경우에만 변경
        if (userPatchRequestDto.getUsername() != null) {
            userEntity.changeUsername(userPatchRequestDto.getUsername());
        }
        if (userPatchRequestDto.getEmail() != null) {
            userEntity.changeEmail(userPatchRequestDto.getEmail());
        }
        if (userPatchRequestDto.getPassword() != null) {
            userEntity.changePassword(userPatchRequestDto.getPassword());
        }

        return modelMapper.map(userEntity, UserDto.UserResponseDto.class);
    }
}
