package com.example.mynewfishshop.repos;

import com.example.mynewfishshop.models.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {
    UserToken findByToken(String token);
}
