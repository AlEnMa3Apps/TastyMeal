package com.alenma3apps.backendTastyMeal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.EventCategoryModel;

/**
 * Interfície per accedir a les dades de la taula event_categories de la bd.
 * @author Albert Borras
 */
@Repository
public interface IEventCategoryRepository extends JpaRepository<EventCategoryModel, Long> {

    @Query("SELECT c FROM EventCategoryModel c WHERE c.category = :categoryName")
    Optional<EventCategoryModel> findByCategory(String categoryName);

}
