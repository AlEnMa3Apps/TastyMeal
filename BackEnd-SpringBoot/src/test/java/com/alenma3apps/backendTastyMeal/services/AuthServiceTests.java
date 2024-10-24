package com.alenma3apps.backendTastyMeal.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.alenma3apps.backendTastyMeal.dto.request.LoginRequest;
import com.alenma3apps.backendTastyMeal.dto.response.LoginResponse;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe AuthService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;
    
    @InjectMocks
    private AuthService authService;

    /**
     * Test per comprovar que l'inici de sessió és correcte.
     * @author Albert Borras
     */
    @Test
    public void AuthServiceTest_login_Success() {
        LoginRequest request = new LoginRequest();
        request.setUsername("UserTest");
        request.setPassword("1234");

        String role = "USER";

        when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword())))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(true);

        LoginResponse login = authService.login(request, role);

        assertEquals(role, login.getRole());
        assertEquals(true, login.getToken() != null && !login.getToken().isEmpty());
    }

    /**
     * Test per comprovar que l'inici de sessió és incorrecte.
     * @author Albert Borras
     */
    @Test
    public void AuthServiceTest_login_failure() {
        LoginRequest request = new LoginRequest();
        request.setUsername("UserTest");
        request.setPassword("1234");

        String role = "USER";

        when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword())))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenThrow(new RuntimeException("Invalid login"));

        assertThrows(RuntimeException.class, () -> authService.login(request, role));
    }

    /**
     * Test per comprovar la generació del token de sessió.
     * @author Albert Borras
     */
    @Test
    public void AuthServiceTest_generateToken() {
        String token = authService.generateToken("UserTest");
        assertEquals(true, token != null && !token.isEmpty());
    }

    /**
     * Test per comprovar la validació del token de sessió.
     * @author Albert Borras
     */
    @Test
    public void AuthServiceTest_validateToken() {
        String username = "UserTest";
        String token = authService.generateToken(username);

        Boolean validateToken = authService.validateToken(token, username);

        assertEquals(true, validateToken);
    }
}