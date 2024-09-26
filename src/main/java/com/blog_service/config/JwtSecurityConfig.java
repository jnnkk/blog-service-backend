package com.blog_service.config;

import com.blog_service.service.CustomUserDetailsService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder; // 순환 참조 문제때문에 따로 분리

    // Spring Security 설정
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // 접속 요청 URL에 따른 권한 설정
        http    .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/users").permitAll()
                    .anyRequest().authenticated());

        http    .csrf(AbstractHttpConfigurer::disable) // CSRF 보안 설정 비활성화
                .httpBasic(Customizer.withDefaults()) // HTTP Basic 인증 비활성화, 활성화하면 로그인창이 뜸
                .formLogin(AbstractHttpConfigurer::disable); // 폼 로그인 비활성화

        http    .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // OAuth2 리소스 서버 설정
                .sessionManagement((session) ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                    .addHeaderWriter(
                        new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));


        return http.build();
    }

//    // CORS 설정
//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // 모든 요청에 대해
//                        .allowedOrigins("/**") // 모든 출처에 대해
//                        .allowedMethods("*"); // 모든 메소드 허용
//            }
//        };
//    }

    // ☆☆☆ AuthenticationManager 설정, 이거 안해줘서 로그인이 안됐었음 ☆☆☆
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService) // DB에서 유저 정보를 가져오는 서비스로 설정
                .passwordEncoder(passwordEncoder); // 패스워드 인코더 설정
        return authenticationManagerBuilder.build();
    }

    // JWT이용을 위한 키 페어 설정
    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // RSA 알고리즘 사용
        keyPairGenerator.initialize(2048); // 키 길이 설정
        return keyPairGenerator.generateKeyPair();
    }

    // keypair를 이용해서 RSA 키 설정
    @Bean
    public RSAKey rsaKey(KeyPair keyPair) throws NoSuchAlgorithmException {
        return new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    // RSA키를 이용해서 JWKSource 설정
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkset = new JWKSet(rsaKey);

        return (jwkSelector, context) -> jwkSelector.select(jwkset);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    // RSA키를 이용해서 JwtDecoder 설정
    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }
}
