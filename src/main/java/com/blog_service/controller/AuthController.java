package com.blog_service.controller;

import com.blog_service.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService;

    @PostMapping("/auth")
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(jwtTokenService.createToken(authentication));
    }
}

// JWT 응답 객체
record JwtResponse(String token) {
}
