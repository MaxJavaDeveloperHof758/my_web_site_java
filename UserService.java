package com.example.mynewfishshop.services;

import com.example.mynewfishshop.models.User;
import com.example.mynewfishshop.repos.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(User user) {
        System.out.println("Регистрация пользователя: "+user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ROLE_USER")); // Назначение роли по умолчанию
        userRepository.save(user);
    }
    public boolean existsByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean authenticate(String email, String password) {
        User user = (User) userRepository.findByEmail(email).orElse(null);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void updateUser(User user, String password) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден с ID: " + user.getId()));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (password != null && !password.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(password)); // Кодируем пароль
        }

        userRepository.save(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + user.getUsername()); // Логирование
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
