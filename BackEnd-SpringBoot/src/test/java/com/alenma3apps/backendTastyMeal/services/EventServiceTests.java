package com.alenma3apps.backendTastyMeal.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alenma3apps.backendTastyMeal.dto.request.EventRequest;
import com.alenma3apps.backendTastyMeal.models.EventCategoryModel;
import com.alenma3apps.backendTastyMeal.models.EventModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IEventCategoryRepository;
import com.alenma3apps.backendTastyMeal.repositories.IEventRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe EventService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class EventServiceTests {

     @Mock
    IEventRepository eventRepository;

    @Mock
    IEventCategoryRepository eventCategoryRepository;

    @Mock
    IUserRepository userRepository;
    
    @InjectMocks
    private EventService eventService;

    private EventModel event;
    private EventRequest request;
    private EventCategoryModel category;
    private EventModel savedEvent;
    private List<EventModel> events;
    private List<UserModel> attendees;
    private UserModel user;

    @BeforeEach
    public void init() {
        category = new EventCategoryModel();
        category.setCategory("Category");
        eventCategoryRepository.save(category);

        attendees = new ArrayList<UserModel>();
        events = new ArrayList<EventModel>();

        event = new EventModel();
        event.setDate("12/02/2025");
        event.setDescription("Insert description here.");
        event.setDuration(120);
        event.setEventCategory(category);
        event.setTitle("Insert title here.");
        event.setAttendee(attendees);
        eventRepository.save(event);

        request = new EventRequest();
        request.setDate("12/03/2025");
        request.setDescription("Insert description here.");
        request.setDuration(90);
        request.setEventCategory("category");
        request.setTitle("Insert title here.");

        savedEvent = event;

        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");
        user.setEvents(events);

        
    }

    /**
     * Test per comprovar que es crea l'esdeveniment.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_createEvent() {
        when(eventCategoryRepository.findByCategory(Mockito.any(String.class))).thenReturn(Optional.ofNullable(category));
        when(eventRepository.save(Mockito.any(EventModel.class))).thenReturn(savedEvent);

        EventModel result = eventService.createEvent(request);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'obté un llistat de tots els esdeveniments.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_getAllEvents() {
        ArrayList<EventModel> listEvents = new ArrayList<EventModel>();
        listEvents.add(savedEvent);

        when(eventRepository.findAll()).thenReturn(listEvents);

        List<EventModel> result = eventService.getAllEvents();
        
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'obté un esdeveniment pel seu id.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_getEventById() {
        when(eventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(savedEvent));

        EventModel result = eventService.getEventById((long) 1);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'edita un esdeveniment pel seu id.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_editEventById() {
        when(eventCategoryRepository.findByCategory(Mockito.any(String.class))).thenReturn(Optional.ofNullable(category));
        when(eventRepository.findById(1L)).thenReturn(Optional.ofNullable(savedEvent));

        ResponseEntity<?> result = eventService.editEventById(1L, request);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'elimina un esdeveniment.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_deleteEventById() {
        when(eventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(savedEvent));

        ResponseEntity<?> result = eventService.deleteEventById((long) 1);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que l'usuari es registra a l'esdeveniment.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_registerUserToEvent() {
        when(eventRepository.findById((long) 1)).thenReturn(Optional.ofNullable(event));

        ResponseEntity<?> result = eventService.registerUserToEvent((long) 1, user);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'obtenen tots els esdeveniments on l'usuari s'ha registrat.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_getEventsRegistered() {
        ResponseEntity<?> result = eventService.getEventsRegistered(user);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que l'usuari s'esborra de l'esdeveniment.
     * @author Albert Borras
     */
    @Test
    public void EventServiceTest_unregisterUserToEvent() {
        when(eventRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(event));

        events.add(event);
        attendees.add(user);

        ResponseEntity<?> result = eventService.deleteEventById((long) 1);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}