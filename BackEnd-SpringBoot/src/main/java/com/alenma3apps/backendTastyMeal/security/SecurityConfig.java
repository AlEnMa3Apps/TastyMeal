package com.alenma3apps.backendTastyMeal.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

import java.util.Optional;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IUserRepository userRepository;

    // Configura la autenticación
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
        http.getSharedObject(AuthenticationManagerBuilder.class);
    
        authenticationManagerBuilder
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
    
        return authenticationManagerBuilder.build();
    }

    // Configura las reglas de seguridad para las solicitudes HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/auth/**").permitAll() // Permite acceso a las rutas de autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            );
        return http.build();
    }

    // Servicio de usuarios personalizado para la autenticación
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<UserModel> user = userRepository.findByUsername(username);
            if (user != null) {
                return org.springframework.security.core.userdetails.User
                        .withUsername(user.get().getUsername())
                        .password(user.get().getPassword())
                        .roles("USER") // Puedes configurar los roles y permisos aquí
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }

    // Definición del codificador de contraseñas (BCrypt en este caso)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}