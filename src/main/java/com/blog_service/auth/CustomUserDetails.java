package com.blog_service.auth;

import com.blog_service.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 유저 권한을 리턴
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // 유저 비밀번호를 리턴
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        // 유저 아이디를 리턴
        return userEntity.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았는지 리턴
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠기지 않았는지 리턴
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격이 유효한지 리턴
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화 되었는지 리턴
        return true;
    }
}
