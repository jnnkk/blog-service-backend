package com.blog_service.auth;

import com.blog_service.entity.UserEntity;
import com.blog_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 -> " + userId));
        System.out.println("이거 작동중 : CustomUserDetailsService.loadUserByUsername");

        return new CustomUserDetails(userEntity);
    }
}
