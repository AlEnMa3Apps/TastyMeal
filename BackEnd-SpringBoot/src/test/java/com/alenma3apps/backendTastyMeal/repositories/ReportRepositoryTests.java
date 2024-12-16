package com.alenma3apps.backendTastyMeal.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.ReportModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;

/**
 * Classe test per testejar les funcions utilitzades de 
 * la interfície IReportRepository per interactuar amb 
 * la base de dades.
 * @author Albert Borras
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReportRepositoryTests {

    @Autowired
    private IReportRepository reportRepository;
    @Autowired
    private IRecipeRepository recipeRepository;

    /**
     * Test per comprovar que el report es guarda a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void ReportRepositoryTest_saveReport() {
        ReportModel report = new ReportModel();
        report.setReport("Aquesta recepta no especifica els al·lèrgens.");
        report.setRecipe(new RecipeModel());
        report.setUser(new UserModel());

        ReportModel savedReport = reportRepository.save(report);

        Assertions.assertThat(savedReport).isNotNull();
        Assertions.assertThat(savedReport.getId()).isGreaterThan(0);
    }

    /**
     * Test per comprovar que s'obté el llistat de reports de la recepta 
     * passada per paràmetre de la base de dades.
     * @author Albert Borras
     */
    @Test
    public void ReportRepositoryTest_findByRecipe() {
        RecipeModel recipe = new RecipeModel();
        recipe.setCookingTime(20);
        recipe.setDescription("Insert description here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setIngredients("Insert ingredients here.");
        recipe.setNumPersons(4);
        recipe.setTitle("Insert title here.");
        recipeRepository.save(recipe);

        List<ReportModel> returnListReports = reportRepository.findByRecipe(
            recipe);

            Assertions.assertThat(returnListReports).isNotNull();
    }

    /**
     * Test per comprovar que s'obté el report amb l'id passat per paràmetre de la base de dades.
     * @author Albert Borras
     */
    @Test
    public void ReportRepositoryTest_findById() {
        ReportModel report = new ReportModel();
        report.setReport("Aquesta recepta no especifica els al·lèrgens.");
        report.setRecipe(new RecipeModel());
        report.setUser(new UserModel());
        reportRepository.save(report);

        Optional<ReportModel> returnedReport = reportRepository.findById(report.getId());

        Assertions.assertThat(returnedReport).isNotNull();
        Assertions.assertThat(returnedReport.get().getId()).isGreaterThan(0);
    }

    /**
     * Test per comprovar que s'elimina un report de la base de dades.
     * @author Albert Borras
     */
    @Test
    public void ReportRepositoryTest_delete() {
        ReportModel report = new ReportModel();
        report.setReport("Aquesta recepta no especifica els al·lèrgens.");
        report.setRecipe(new RecipeModel());
        report.setUser(new UserModel());
        reportRepository.save(report);

        reportRepository.delete(report);

        Optional<ReportModel> reportReturn = reportRepository.findById(report.getId());
        Assertions.assertThat(reportReturn).isEmpty();
    }
}