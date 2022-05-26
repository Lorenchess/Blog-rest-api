package com.lorenchess.blogrestapi.repository;

import com.lorenchess.blogrestapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findByUsername(String username);
    boolean existsUserEntityByUsername(String username);
    boolean existsUserEntityByEmail(String email);

}
