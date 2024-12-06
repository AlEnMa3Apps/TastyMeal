package com.alenma3apps.backendTastyMeal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;

/**
 * Interfície per accedir a les dades de la taula comments de la bd.
 * @author Albert Borras
 */
@Repository
public interface ICommentRepository extends JpaRepository<CommentModel, Long> {
    List<CommentModel> findByRecipe(RecipeModel recipe);

}
