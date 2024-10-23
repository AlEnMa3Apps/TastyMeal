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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;

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

        ResultActions response = mockMvc.perform(get("/api/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(listUsers)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listUsers.size()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(user1.getUsername()))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value(user2.getUsername()));
    }

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

    @Test
    public void UserControllerTest_deleteUserById_ok() throws Exception {
        String message = "User with id 1 deleted";

        given(userService.deleteUserById(ArgumentMatchers.any())).willReturn(true);

        ResultActions response = mockMvc.perform(delete("/api/user/1")
        .contentType(MediaType.TEXT_EVENT_STREAM_VALUE)
        .content(message));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(message));
    }

    @Test
    public void UserControllerTest_deleteUserById_error() throws Exception {
        String message = "Error deleting user with id 1";

        given(userService.deleteUserById(ArgumentMatchers.any())).willReturn(false);

        ResultActions response = mockMvc.perform(delete("/api/user/1")
        .contentType(MediaType.TEXT_EVENT_STREAM_VALUE)
        .content(message));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(message));
    }
}
