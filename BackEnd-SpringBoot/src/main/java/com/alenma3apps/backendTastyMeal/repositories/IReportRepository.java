package com.alenma3apps.backendTastyMeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.ReportModel;
import java.util.List;


/**
 * Interfície per accedir a les dades de la taula reports de la bd.
 * @author Albert Borras
 */
@Repository
public interface IReportRepository extends JpaRepository<ReportModel, Long> {
    List<ReportModel> findByRecipe(RecipeModel recipe);
}
