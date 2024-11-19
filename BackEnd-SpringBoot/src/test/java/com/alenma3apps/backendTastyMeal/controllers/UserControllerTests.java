package com.alenma3apps.backendTastyMeal.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.UserService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe UserController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;
    
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test per comprovar el correcte funcionament de l'endpoint 
     * per sol·licitar el llistat de tots els usuaris.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void UserControllerTest_getUsers() throws Exception {
        ArrayList<UserModel> listUsers = new ArrayList<>();

        UserModel user1 = new UserModel();
        user1.setUsername("UserTest1");
        user1.setEmail("test1@test.com");
        listUsers.add(user1);

        UserModel user2 = new UserModel();
        user2.setUsername("UserTest2");
        user2.setEmail("test2@test.com");
        listUsers.add(user2);

        given(userService.getUsers()).willReturn(listUsers);

        ResultActions response = mockMvc.perform(get("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(listUsers)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listUsers.size()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(user1.getUsername()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value(user2.getUsername()));
    }

    /**
     * Test per comprovar el correcte funcionament de l'endpoint 
     * per sol·licitar les dades d'un usuari amb el paràmetre id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void UserControllerTest_getUserById() throws Exception {
        UserModel userReturn = new UserModel();
        userReturn.setActive(true);
        userReturn.setEmail("test@test.com");
        userReturn.setFirstName("Test");
        userReturn.setLastName("Unit");
        userReturn.setPassword("1234");
        userReturn.setRole(RoleModel.USER);
        userReturn.setUsername("UserTest");

        given(userService.getUserById(ArgumentMatchers.any())).willReturn(Optional.ofNullable(userReturn));

        ResultActions response = mockMvc.perform(get("/api/user/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userReturn)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userReturn.getUsername()));
    }

    /**
     * Test per comprovar el correcte funcionament de l'endpoint 
     * per eliminar un usuari amb el paràmetre id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void UserControllerTest_deleteUserById_ok() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "USER_DELETED"), HttpStatus.OK);

        given(userService.deleteUserById(1L)).willAnswer(invocation -> responseExpect);


        ResultActions response = mockMvc.perform(delete("/api/user/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(responseExpect)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el correcte funcionament de l'endpoint 
     * per quan no s'elimina un usuari amb el paràmetre id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void UserControllerTest_deleteUserById_error() throws Exception {
        
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_NOT_DELETED"), HttpStatus.BAD_REQUEST);

        given(userService.deleteUserById(1L)).willAnswer(invocation -> responseExpect);


        ResultActions response = mockMvc.perform(delete("/api/user/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(responseExpect)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
