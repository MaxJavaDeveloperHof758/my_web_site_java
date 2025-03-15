package com.example.mynewfishshop.repos;

import com.example.mynewfishshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
