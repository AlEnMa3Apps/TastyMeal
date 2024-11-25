package com.alenma3apps.backendTastyMeal.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe que gestiona la lògica de la verificació de l'usuari.
 * @author Albert Borras
 */
@Service
public class JwtService {

    @Autowired
    private IUserRepository userRepository;

    private long expirationTime = 1000 * 60 * 60 * 10; // 10 hores

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

/**
     * Funció per generar el token.
     * @param username Nom de l'usuari.
     * @return Token.
     * @author Albert Borras
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
     * @param token Token de l'usuari.
     * @param username Nom de l'usuari.
     * @return Retorna true si el token és vàlid, del contrari retorna false.
     * @author Albert Borras
     */
    public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token)); 
    }

    /**
     * Funció per obtenir el token de la capçalera de la petició.
     * @param request dades d'entrada de la petició HTTP.
     * @return token o null
     * @author Albert Borras
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;

    }

    /**
     * Funció per validar el token i obtenir el username.
     * @param header Capçalera de la petició HTTP.
     * @return ValidationResponse amb el nom d'usuari i un booleà amb true si és vàlid, 
     * del contrari false.
     */
    public ValidationResponse validateTokenAndUser(HttpServletRequest header) {
        String token = getTokenFromRequest(header);
        boolean isValid = false;

        if (token != null) {
            String username = extractUsername(token);
            UserModel user = userRepository.findByUsername(username).orElse(null);
            isValid = validateToken(token, user.getUsername());

            return new ValidationResponse(username, isValid);
        }
        return null;
    }

    /**
     * Funció per extreure el nom d'usuari del token passat per paràmetre.
     * @param token Token de l'usuari.
     * @return El nom d'usuari.
     * @author Albert Borras
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Funció per extreure el cos del token on conté totes les dades.
     * @param token Token de l'usuari.
     * @return El cos del token.
     * @author Albert Borras
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Funció per comprovar si el token passat per paràmetre està caducat.
     * @param token Token de l'usuari.
     * @return Retorna true si està caducat, del contrari retorna false.
     * @author Albert Borras
     */
    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
