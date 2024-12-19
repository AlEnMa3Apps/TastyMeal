package com.alenma3apps.backendTastyMeal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.request.EventRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.EventModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.EventService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controladora que conté els endpoints de gestió de esdeveniments.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserRepository userRepository;
    
    /**
     * Endpoint per crear un esdeveniment.
     * @param request Paràmetres del nou esdeveniment.
     * @param header Capçalera de la petició http.
     * @return Codi de la petició i estat de la petició.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PostMapping("/event/create")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest request, HttpServletRequest header) {
        if (eventService.eventExists(request)) {
            return SpringResponse.eventAlreadyExist();
        }

        EventModel event = eventService.createEvent(request);

        if (event != null){
            return SpringResponse.eventCreated();
        } else {
            return SpringResponse.errorCreatingEvent();
        }
    }

    /**
     * Endpoint per obtenir un esdeveniment pel seu Id.
     * @param id Id de l'esdeveniment
     * @return L'esdeveniment o en cas d'error el missatge d'error.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        EventModel event = eventService.getEventById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return SpringResponse.eventNotFound();
        }
    }

    /**
     * Endpoint per obtenir tots els esdeveniments.
     * @return Llistat amb tots els esdeveniments o en cas d'error 
     * el missatge d'error.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("/event/all")
    public ResponseEntity<?> getAllEvents() {
        List<EventModel> listEvents = eventService.getAllEvents();

        if (listEvents != null) {
            return ResponseEntity.ok(listEvents);
        } else {
            return SpringResponse.eventsNotFound();
        }
    }

    /**
     * Endpoint per editar un esdeveniment amb el paràmetre id.
     * @param id Id de l'esdeveniment a editar.
     * @param request Paràmetres per editar l'esdeveniment.
     * @return Missatge notificant si s'ha editat o no l'esdeveniment.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @PutMapping("/event/{id}")
    public ResponseEntity<?> editEventByid(@PathVariable Long id, @RequestBody EventRequest request) {
        return eventService.editEventByid(id, request);
    }

    /**
     * Endpoint per eliminar un esdeveniment pel paràmetre id.
     * @param id Id de l'esdeveniment a eliminar.
     * @return Missatge notificant si s'ha eliminat o no l'esdeveniment.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Long id) {
        return eventService.deleteEventById(id);
    }

    /**
     * Endpoint per registrar l'usuari que fa la petició a l'esdeveniment.
     * @param id Id de l'esdeveniment.
     * @param header Capçalera de la petició http.
     * @return Missatge notificant si l'usuari s'ha registrat o no a l'esdeveniment.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PostMapping("/event/{id}/register")
    public ResponseEntity<?> registerUserToEvent(@PathVariable Long id, HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);

        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        ResponseEntity<?> response;

        try {
            response = eventService.registerUserToEvent(id, user);
        } catch (Exception e) {
            response = SpringResponse.errorRegisteringUserToEvent();
        }
        return response;
    }

    /**
     * Endpoint per obtenir els esdeveniments on l'usuari està registrat.
     * @param header Capçalera de la petició http.
     * @return Llistat dels esdeveniments on l'usuari està registrat.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("/events/registered")
    public ResponseEntity<?> getEventsRegisteredByUserId(HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);

        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        ResponseEntity<?> response;

        try {
            response = eventService.getEventsRegistered(user);
        } catch (Exception ex) {
            response = SpringResponse.errorGettingEventsRegistered();
        }
        return response;
    }

    /**
     * Endpoint per suprimir l'usuari que fa la petició a l'esdeveniment.
     * @param id Id de l'esdeveniment.
     * @param header Capçalera de la petició http.
     * @return Missatge notificant si l'usuari s'ha suprimit o no de l'esdeveniment.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @DeleteMapping("/event/{id}/unregister")
    public ResponseEntity<?> unregisterUserToEvent(@PathVariable Long id, HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);

        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        ResponseEntity<?> response;

        try {
            response = eventService.unregisterUserToEvent(id, user);
        } catch (Exception e) {
            response = SpringResponse.errorUnregisteringUserToEvent();
        }
        return response;
    }
}