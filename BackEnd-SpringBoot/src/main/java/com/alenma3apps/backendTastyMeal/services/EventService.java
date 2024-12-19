package com.alenma3apps.backendTastyMeal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.EventRequest;
import com.alenma3apps.backendTastyMeal.models.EventCategoryModel;
import com.alenma3apps.backendTastyMeal.models.EventModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IEventCategoryRepository;
import com.alenma3apps.backendTastyMeal.repositories.IEventRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

/**
 * Classe que gestiona la lògica de les receptes.
 * @author Albert Borras
 */
@Service
public class EventService {

    @Autowired
    private IEventRepository eventRepository;
    
    @Autowired
    private IEventCategoryRepository eventCategoryRepository;

    @Autowired
    private IUserRepository userRepository;

    /**
     * Crea l'esdeveniment a la base de dades.
     * @param request Paràmetres de l'esdeveniment.
     * @return Esdeveniment creat.
     * @author Albert Borras
     */
    public EventModel createEvent(EventRequest request) {

        Optional<EventCategoryModel> categoryOptional = eventCategoryRepository.findByCategory(request.getEventCategory());
        EventCategoryModel category = categoryOptional.get();
        
        EventModel event = new EventModel();
        event.setDate(request.getDate());
        event.setDescription(request.getDescription());
        event.setDuration(request.getDuration());
        event.setEventCategory(category);
        event.setTitle(request.getTitle());

        return eventRepository.save(event);

    }

    /**
     * Obté tots els esdeveniments de la base de dades.
     * @return Llistat dels esdeveniments.
     * @author Albert Borras
     */
    public List<EventModel> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Obté l'esdeveniment passat pel paràmetre id de la base de dades.
     * @param id Id de l'esdeveniment.
     * @return Esdeveniment.
     * @author Albert Borras
     */
    public EventModel getEventById(Long id) {
        Optional<EventModel> eventOptional = eventRepository.findById(id);

        if (!eventOptional.isPresent()) {
            return null;
        }

        return eventOptional.get();
    }

    /**
     * Edita un esdeveniment pel seu paràmetre id.
     * @param id Id de l'esdeveniment a editar.
     * @param request Paràmetres de l'esdeveniment a editar.
     * @return Missatge confirmant 
     */
    public ResponseEntity<?> editEventByid(long id, EventRequest request) {
        Optional<EventModel> eventOptional = eventRepository.findById(id);
        if (!eventOptional.isPresent()) {
            return SpringResponse.eventNotFound();
        }

        Optional<EventCategoryModel> categoryOptional = eventCategoryRepository.findByCategory(request.getEventCategory());
        EventCategoryModel category = categoryOptional.get();

        EventModel event = eventOptional.get();
        
        try {
            event.setDate(request.getDate());
            event.setDescription(request.getDescription());
            event.setDuration(request.getDuration());
            event.setEventCategory(category);
            event.setTitle(request.getTitle());

            eventRepository.save(event);
            return SpringResponse.eventUpdated();
        } catch (Exception ex) {
            return SpringResponse.errorUpdatingEvent();
        }
    }

    /**
     * Elimina un esdeveniment pel paràmetre id.
     * @param id Id de l'esdeveniment a eliminar.
     * @return Missatge confirmant si s'ha eliminat o no l'esdeveniment.
     */
    public ResponseEntity<?> deleteEventById(Long id) {
        Optional<EventModel> eventOptional = eventRepository.findById(id);
        if (!eventOptional.isPresent()) {
            return SpringResponse.eventNotFound();
        }

        EventModel event = eventOptional.get();
        
        try{
            eventRepository.delete(event);
            return SpringResponse.eventDeleted();
        } catch (Exception ex) {
            return SpringResponse.errorDeletingEvent();
        }
    }

    /**
     * Registra l'usuari del paràmetre a l'esdeveniment passat pel paràmetre id.
     * @param eventId Id de l'esdeveniment.
     * @param user Usuari a registar a l'esdeveniment.
     * @return Missatge notificant si s'ha registrat o no l'usuari a l'esdeveniment.
     */
    public ResponseEntity<?> registerUserToEvent(Long eventId, UserModel user) {
        Optional<EventModel> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isEmpty()) {
            return SpringResponse.eventNotFound();
        }

        EventModel event = eventOptional.get();

        if (!user.getEvents().contains(event)) {
            user.getEvents().add(event);
            event.getAttendee().add(user);

            userRepository.save(user);
            eventRepository.save(event);
            return SpringResponse.userRegisteredToEvent();
        } else {
            return SpringResponse.userAlreadyRegisteredToEvent();
        }
    }

    /**
     * Obté el llistat d'esdeveniments registats de l'usuari passat per paràmetre.
     * @param user Usuari a qui comprovar els esdeveniments on està registrat.
     * @return Llistat dels esdeveniments on està registrat l'usuari.
     */
    public ResponseEntity<?> getEventsRegistered(UserModel user) {
        List<Long> eventsRegisteredId = new ArrayList<>();
        user.getEvents().forEach( it ->
            {
                assert false;
                eventsRegisteredId.add(it.getId());
            }
        );
        return ResponseEntity.ok(eventsRegisteredId);
    }

    /**
     * Suprimeix l'usuari del paràmetre a l'esdeveniment passat pel paràmetre id.
     * @param eventId Id de l'esdeveniment.
     * @param user Usuari a registar a l'esdeveniment.
     * @return Missatge notificant si s'ha registrat o no l'usuari a l'esdeveniment.
     */
    public ResponseEntity<?> unregisterUserToEvent(Long eventId, UserModel user) {
        Optional<EventModel> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isEmpty()) {
            return SpringResponse.eventNotFound();
        }

        EventModel event = eventOptional.get();

        if (user.getEvents().contains(event)) {
            user.getEvents().remove(event);
            event.getAttendee().remove(user);

            userRepository.save(user);
            eventRepository.save(event);
            return SpringResponse.userUnregisteredToEvent();
        } else {
            return SpringResponse.userNotRegisteredToEvent();
        }
    }

    /**
     * Comprova pel títol de l'esdeveniment si existeix a la base de dades.
     * @param request Esdeveniment a comprovar
     * @return True si existeix, del contrari retorna false.
     * @author Albert Borras
     */
    public Boolean eventExists(EventRequest request) {
        Optional<EventModel> checkEvent = eventRepository.findByTitle(request.getTitle());
        if (checkEvent.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
    
}
