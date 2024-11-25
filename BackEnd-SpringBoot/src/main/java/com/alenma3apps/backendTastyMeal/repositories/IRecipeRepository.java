package com.alenma3apps.backendTastyMeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;

import java.util.List;
import java.util.Optional;


/**
 * Interfície per accedir a les dades de la taula recipes de la bd.
 * @author Albert Borras
 */
@Repository
public interface IRecipeRepository extends JpaRepository<RecipeModel, Long>{
    List<RecipeModel> findByOwnerId(UserModel user);

    Optional<RecipeModel> findByTitle(String title);
}
