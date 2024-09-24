package com.blog_service.repository;

import com.blog_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserId(String userId);

    boolean existsByUserId(String userId);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
