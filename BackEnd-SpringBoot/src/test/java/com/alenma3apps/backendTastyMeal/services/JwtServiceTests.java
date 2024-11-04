package com.alenma3apps.backendTastyMeal.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alenma3apps.backendTastyMeal.security.JwtService;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe JwtService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class JwtServiceTests {

    @InjectMocks
    private JwtService jwtService;

    /**
     * Test per comprovar la generació del token de sessió.
     * @author Albert Borras
     */
    @Test
    public void JwtServiceTest_generateToken() {
        String token = jwtService.generateToken("UserTest");
        assertEquals(true, token != null && !token.isEmpty());
    }

    /**
     * Test per comprovar la validació del token de sessió.
     * @author Albert Borras
     */
    @Test
    public void JwtServiceTest_validateToken() {
        String username = "UserTest";
        String token = jwtService.generateToken(username);

        Boolean validateToken = jwtService.validateToken(token, username);

        assertEquals(true, validateToken);
    }
}
