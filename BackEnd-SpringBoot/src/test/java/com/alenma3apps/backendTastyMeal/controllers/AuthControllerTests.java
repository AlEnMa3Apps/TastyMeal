package com.alenma3apps.backendTastyMeal.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import com.alenma3apps.backendTastyMeal.dto.request.RegisterRequest;
import com.alenma3apps.backendTastyMeal.dto.request.LoginRequest;
import com.alenma3apps.backendTastyMeal.dto.response.LoginResponse;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.AuthService;
import com.alenma3apps.backendTastyMeal.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe AuthController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;
    
    @MockBean
    private JwtService jwtService;

    @MockBean
    private IUserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private UserModel user;
    private LoginResponse loginResponse;

    @BeforeEach
    public void init() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@test.com");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("Unit");
        registerRequest.setPassword("1234");
        registerRequest.setUsername("UserTest");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("UserTest");
        loginRequest.setPassword("1234");

        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        loginResponse = new LoginResponse();
        loginResponse.setToken("token");
        loginResponse.setRole("USER");
    }

    /**
     * Test per comprovar el correcte funcionament 
     * de l'endpoint per iniciar sessió.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void AuthControllerTest_login_responseOk() throws Exception {

        user.setPassword(passwordEncoder.encode("1234"));
        
        given(userRepository.findByUsername(ArgumentMatchers.anyString())).willReturn(Optional.ofNullable(user));
        given(authService.login(ArgumentMatchers.any(LoginRequest.class), ArgumentMatchers.anyString())).willReturn(loginResponse);

        ResultActions response = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(user.getRole().toString()));
        
    }

    /**
     * Test per comprovar el funcionament 
     * de l'endpoint per iniciar sessió quan no troba l'usuari.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void AuthControllerTest_login_responseNotFound() throws Exception {

        given(userRepository.findByUsername(loginRequest.getUsername())).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test per comprovar el funcionament 
     * de l'endpoint per iniciar sessió quan la contrasenya és incorrecte.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void AuthControllerTest_login_responseBadRequest() throws Exception {

        given(userRepository.findByUsername(loginRequest.getUsername())).willReturn(Optional.ofNullable(user));
        
        ResultActions response = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    /**
     * Test per comprovar el funcionament 
     * de l'endpoint per registrar un usuari.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void AuthControllerTest_registerUser() throws Exception {

        given(userService.registerUser(ArgumentMatchers.any(RegisterRequest.class))).willReturn(user);

        ResultActions response = mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(registerRequest.getEmail()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(registerRequest.getFirstName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(registerRequest.getLastName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(registerRequest.getPassword()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(registerRequest.getUsername()));
    }
}