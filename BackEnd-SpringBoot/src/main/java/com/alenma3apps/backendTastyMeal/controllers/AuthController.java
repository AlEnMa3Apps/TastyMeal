package com.alenma3apps.backendTastyMeal.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.request.LoginRequest;
import com.alenma3apps.backendTastyMeal.dto.request.RegisterRequest;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.services.AuthService;
import com.alenma3apps.backendTastyMeal.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
         Optional<UserModel> userOptional = userRepository.findByUsername(request.getUsername());

        if(!userOptional.isPresent()){
            return ResponseEntity.ok(authService.login(request));
        }
        String passwordRequest = request.getPassword();
        String userPassword = userOptional.get().getPassword();
         if(!passwordEncoder.matches(passwordRequest,userPassword))
             return ResponseEntity.ok(authService.login(request));


        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(path = "/register")
    public UserModel registerUser(@RequestBody RegisterRequest request) {
        return this.userService.registerUser(request);
    }

}
