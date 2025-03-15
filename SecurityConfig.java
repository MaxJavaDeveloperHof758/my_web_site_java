package com.example.mynewfishshop.configures;

import com.example.mynewfishshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login","/logout", "/register","/about","/contacts").permitAll() // Доступ всем
                        .requestMatchers("/*.css","/css/**", "/js/**","/styles/**").permitAll() // Разрешить доступ к CSS и другим ресурсам
                        .requestMatchers("/public/**").permitAll() // Доступ для страниц, начинающихся с /public/ (например, /public/about)
                        .requestMatchers("/user/**").authenticated() // Доступ для авторизованных пользователей
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Доступ только для администраторов
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/dashboard", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }
}
