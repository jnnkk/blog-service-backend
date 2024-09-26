package com.blog_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtEncoder jwtEncoder;

    @PostMapping("/auth")
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    // JWT 토큰 생성
    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self") // 토큰 발급자
                .issuedAt(Instant.now()) // 토큰 발급 시간
                .expiresAt(Instant.now().plusSeconds((60 * 30))) // 토큰 만료 시간 (30분)
                .subject(authentication.getName()) // 토큰 제목
                .claim("scope", createScope(authentication)) // 스코프?
                .build();

        System.out.println("이거 작동중 : AuthController.createToken");

        return jwtEncoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    // 스코프 생성, 스코프는 권한을 의미
    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }
}

// JWT 응답 객체
record JwtResponse(String token) {
}
