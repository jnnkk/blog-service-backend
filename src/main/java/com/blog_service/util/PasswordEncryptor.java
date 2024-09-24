package com.blog_service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
    public void encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(password);
        System.out.println("PasswordHash : " + encryptedPassword);
    }

    public static void main(String[] args) {
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        passwordEncryptor.encryptPassword("asd");
        passwordEncryptor.encryptPassword("sad");
    }
}


