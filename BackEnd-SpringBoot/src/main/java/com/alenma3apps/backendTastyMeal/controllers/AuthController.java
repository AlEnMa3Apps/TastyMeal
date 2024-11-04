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
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import java.util.Optional;

/**
 * Classe que conté els endpoints per iniciar sessió i registre d'usuari.
 * @author Albert Borras
 */
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
    
    /**
     * Endpoint per iniciar sessió.
     * @param request conté l'usuari i contrasenya per iniciar sessió.
     * @return Codi de l'estat de la petició i si és correcte s'adjunta 
     * el token i el rol de l'usuari.
     * @author Albert Borras
     */
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
         Optional<UserModel> userOptional = userRepository.findByUsername(request.getUsername());

        if(!userOptional.isPresent()){
            return SpringResponse.userNotFound();
        }
        String passwordRequest = request.getPassword();
        String userPassword = userOptional.get().getPassword();
         if(!passwordEncoder.matches(passwordRequest,userPassword))
             return SpringResponse.wrongPassword();

        return ResponseEntity.ok(authService.login(request, userOptional.get().getRole().toString()));
    }

    /**
     * Endpoint per registrar un nou usuari.
     * @param request usuari a registrar.
     * @return Usuari registrat.
     * @author Albert Borras
     */
    @PostMapping(path = "/register")
    public UserModel registerUser(@RequestBody RegisterRequest request) {
        return this.userService.registerUser(request);
    }
}
