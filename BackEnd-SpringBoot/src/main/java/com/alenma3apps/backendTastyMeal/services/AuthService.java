package com.alenma3apps.backendTastyMeal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.LoginRequest;
import com.alenma3apps.backendTastyMeal.dto.response.LoginResponse;
import com.alenma3apps.backendTastyMeal.security.JwtService;

/**
 * Classe que gestiona la lògica de l'inici de sessió.
 * @author Albert Borras
 */
@Service
public class AuthService {

     @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    
    /**
     * Autentica l'usuari passat per paràmetre i si és autenticat genera el 
     * token a partir del nom d'usuari i el retorna juntament amb el rol de l'usuari.
     * Si no s'autentica genera una excepció.
     * @param request L'usuari a iniciar sessió.
     * @param role El rol de l'usuari a iniciar sessió.
     * @return La resposta a la petició de l'inici de sessió amb el token i el rol de l'usuari.
     * @author Albert Borras
     */
    public LoginResponse login(LoginRequest request, String role) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        if (authentication.isAuthenticated()) {
            return LoginResponse.builder()
            .token(jwtService.generateToken(request.getUsername()))
            .role(role)
            .build();
            
        } else {
            throw new RuntimeException("Invalid login");
        }
    }
    
}