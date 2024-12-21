package com.alenma3apps.backendTastyMeal.repositories;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alenma3apps.backendTastyMeal.models.EventCategoryModel;
import com.alenma3apps.backendTastyMeal.models.EventModel;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;

/**
 * Classe test per testejar les funcions utilitzades de 
 * la interfície IEvenetRepository per interactuar amb 
 * la base de dades.
 * @author Albert Borras
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EventRepositoryTests {

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IEventCategoryRepository eventCategoryRepository;

    private EventCategoryModel category;
    private EventModel event;

    @BeforeEach
    public void init() {
        category = new EventCategoryModel();
        category.setCategory("Category");
        eventCategoryRepository.save(category);

        event = new EventModel();
        event.setDate("12/02/2025");
        event.setDescription("Insert description here.");
        event.setDuration(120);
        event.setEventCategory(category);
        event.setTitle("Insert title here.");
        eventRepository.save(event);
    }

    /**
     * Test per comprovar que l'esdeveniment es guarda a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void EventRepositoryTests_save() {
        EventModel savedEvent = eventRepository.save(event);

        Assertions.assertThat(savedEvent).isNotNull();
        Assertions.assertThat(savedEvent.getId()).isGreaterThan(0);
    }

    /**
     * Test per obtenir l'esdeveniment demanat per id a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void EventRepositoryTests_findByid() {
        EventModel returnEvent = eventRepository.findById(event.getId()).get();
        Assertions.assertThat(returnEvent).isNotNull();
    }

    /**
     * Test per obtenir l'esdeveniment demanat pel títol a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void EventRepositoryTests_findByTitle() {
        EventModel returnEvent = eventRepository.findByTitle(event.getTitle()).get();
        Assertions.assertThat(returnEvent).isNotNull();
    }
}
