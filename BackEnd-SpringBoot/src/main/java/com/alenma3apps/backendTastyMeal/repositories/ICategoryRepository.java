package com.alenma3apps.backendTastyMeal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.CategoryModel;

/**
 * Interfície per accedir a les dades de la taula categories de la bd.
 * @author Albert Borras
 */
@Repository
public interface ICategoryRepository extends JpaRepository<CategoryModel, Long>{
    
    @Query("SELECT c FROM CategoryModel c WHERE c.category = :categoryName")
    Optional<CategoryModel> findByCategory(String categoryName);
    
}