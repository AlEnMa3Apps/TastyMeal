package com.alenma3apps.backendTastyMeal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.EventModel;

/**
 * Interfície per accedir a les dades de la taula events de la bd.
 * @author Albert Borras
 */
@Repository
public interface IEventRepository extends JpaRepository<EventModel, Long> {
    Optional<EventModel> findByTitle(String title);
}