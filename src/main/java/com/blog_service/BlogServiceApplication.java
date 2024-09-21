package com.blog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication
public class BlogServiceApplication {

    public static void main(String[] args) {
        ZonedDateTime kstTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"));
        SpringApplication.run(BlogServiceApplication.class, args);
    }

}
