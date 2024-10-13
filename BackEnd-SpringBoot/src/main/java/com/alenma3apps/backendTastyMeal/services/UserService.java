package com.alenma3apps.backendTastyMeal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.LoginRequest;
import com.alenma3apps.backendTastyMeal.dto.request.RegisterRequest;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;


import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ArrayList<UserModel> getUsers() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel registerUser(RegisterRequest request) {
        UserModel user = new UserModel();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole("USER");
        user.setActive(true);

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        if (authentication.isAuthenticated()) {
            return this.authService.generateToken(request.getUsername());
        } else {
            throw new RuntimeException("Invalid login");
        }
    }

    public UserModel findByUsername(String username) {
        UserModel user = null;
        List<UserModel> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if ((user = users.get(i)).getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}