package com.example.mynewfishshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="allusers_table")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name="user_email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>(); // Роли пользователя

    // Дополнительные методы, если необходимо
}
