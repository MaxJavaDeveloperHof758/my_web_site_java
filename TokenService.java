package com.example.mynewfishshop.services;

import com.example.mynewfishshop.models.UserToken;
import com.example.mynewfishshop.repos.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private UserTokenRepository tokenRepository;

    public void createToken(Long userId, String token) {
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);
        tokenRepository.save(userToken);
    }

    public UserToken validateToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
