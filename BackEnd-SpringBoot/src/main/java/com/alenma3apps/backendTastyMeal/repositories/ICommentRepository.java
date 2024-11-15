package com.alenma3apps.backendTastyMeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.CommentModel;

/**
 * Interfície per accedir a les dades de la taula comments de la bd.
 * @author Albert Borras
 */
@Repository
public interface ICommentRepository extends JpaRepository<CommentModel, Long> {

}
