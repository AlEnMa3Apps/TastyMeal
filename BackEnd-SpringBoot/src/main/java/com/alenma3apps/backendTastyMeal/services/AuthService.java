package com.alenma3apps.backendTastyMeal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.LoginRequest;
import com.alenma3apps.backendTastyMeal.dto.response.LoginResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import java.util.HashMap;

/**
 * Classe que gestiona la lògica de l'inici de sessió.
 * @author Albert Borras
 */
@Service
public class AuthService {

     @Autowired
    private AuthenticationManager authenticationManager;

    private long expirationTime = 1000 * 60 * 60 * 10; // 10 hores

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    

    /**
     * Autentica l'usuari passat per paràmetre i si és autenticat genera el 
     * token a partir del nom d'usuari i el retorna juntament amb el rol de l'usuari.
     * Si no s'autentica genera una excepció.
     * @param request L'usuari a iniciar sessió.
     * @param role El rol de l'usuari a iniciar sessió.
     * @return La resposta a la petició de l'inici de sessió amb el token i el rol de l'usuari.
     */
    public LoginResponse login(LoginRequest request, String role) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        if (authentication.isAuthenticated()) {
            return LoginResponse.builder()
            .token(this.generateToken(request.getUsername()))
            .role(role)
            .build();
            
        } else {
            throw new RuntimeException("Invalid login");
        }
    }

    /**
     * Funció per generar el token.
     * @param username Nom de l'usuari.
     * @return Token.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    /**
     * Funció per validar el token.
     * @param token
     * @param username
     * @return Retorna true si el token és vàlid, del contrari retorna false.
     */
    public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Funció per extreure el nom d'usuari del token passat per paràmetre.
     * @param token
     * @return El nom d'usuari.
     */
    private String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Funció per extreure el cos del token on conté totes les dades.
     * @param token
     * @return El cos del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Funció per comprovar si el token passat per paràmetre està caducat.
     * @param token
     * @return Retorna true si està caducat, del contrari retorna false.
     */
    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}