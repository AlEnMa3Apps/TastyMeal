package com.alenma3apps.backendTastyMeal.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

import com.alenma3apps.backendTastyMeal.dto.request.EventRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.EventCategoryModel;
import com.alenma3apps.backendTastyMeal.models.EventModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IEventRepository;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.EventService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe EventController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = EventController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EventControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EventService eventService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IEventRepository eventRepository;

    @MockBean
    private IRecipeRepository recipeRepository;

    //@MockBean
    //private IEventCategoryRepository eventCategoryRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    private EventModel event;
    private EventRequest request;
    private EventCategoryModel category;
    private List<EventModel> events;
    private List<UserModel> attendees;
    private UserModel user;
    private ValidationResponse validationResponse;

    @BeforeEach
    public void init() {
        category = new EventCategoryModel();
        category.setId(1L);
        category.setCategory("Category");
        //eventCategoryRepository.save(category);

        attendees = new ArrayList<UserModel>();
        events = new ArrayList<EventModel>();

        event = new EventModel();
        event.setDate("12/02/2025");
        event.setDescription("Insert description here.");
        event.setDuration(120);
        event.setEventCategory(category);
        event.setTitle("Insert title here.");
        event.setAttendee(attendees);
        //eventRepository.save(event);

        request = new EventRequest();
        request.setDate("12/03/2025");
        request.setDescription("Insert description here.");
        request.setDuration(90);
        request.setEventCategory("category");
        request.setTitle("Insert title here.");

        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");
        user.setEvents(events);

        validationResponse = new ValidationResponse();
        validationResponse.setUsername("UserTest");
        validationResponse.setValid(true);
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per crear un esdeveniment.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_createEvent() throws Exception {
        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(eventService.createEvent(ArgumentMatchers.any(EventRequest.class))).willReturn(event);

        ResultActions response = mockMvc.perform(post("/api/event/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per obtenir un esdeveniment pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_getEventById() throws Exception {
        given(eventService.getEventById(ArgumentMatchers.anyLong())).willReturn(event);
        
        ResultActions response = mockMvc.perform(get("/api/event/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk()); 
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per obtenir el 
     * llistat d'assistents a un esdeveniment pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_getAttendeesFromEvent() throws Exception {
        given(eventService.getEventById(ArgumentMatchers.anyLong())).willReturn(event);
        
        ResultActions response = mockMvc.perform(get("/api/event/1/attendees"));

        response.andExpect(MockMvcResultMatchers.status().isOk()); 
    }


    /**
     * Test per comprovar el funcionament de l'endpoint per obtenir tots els esdeveniments.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_getAllEvents() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/event/all"));
        response.andExpect(MockMvcResultMatchers.status().isOk());  
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per editar un esdeveniment pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_editEventByid() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "EVENT_EDITED"), HttpStatus.OK);

        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(eventService.editEventById(1L, request)).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(put("/api/event/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per eliminar un esdeveniment pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_deleteEventById() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "EVENT_DELETED"), HttpStatus.OK);

        given(eventService.deleteEventById(1L)).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/event/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per registrar un usuari a un esdeveniment.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_registerUserToEvent() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "USER_REGISTERED_TO_EVENT"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername(ArgumentMatchers.anyString())).willReturn(Optional.ofNullable(user));
        given(eventService.registerUserToEvent(ArgumentMatchers.anyLong(), ArgumentMatchers.any(UserModel.class))).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(post("/api/event/1/register"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per obtenir els esdeveniments on l'usuari s'ha registrat.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_getEventsRegisteredOfUser() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "OK"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(eventService.getEventsRegistered(ArgumentMatchers.any(UserModel.class))).willAnswer(invocation -> responseExpect);
        
        ResultActions response = mockMvc.perform(get("/api/events/registered"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar l'endpoint per suprimir un usuari d'un esdeveniment.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void EventControllerTest_unregisterUserToEvent() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "FAVORITE_RECIPE_REMOVED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(eventService.unregisterUserToEvent(ArgumentMatchers.anyLong(), ArgumentMatchers.any(UserModel.class))).willAnswer(invocation -> responseExpect);
        
        ResultActions response = mockMvc.perform(delete("/api/event/1/unregister"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}