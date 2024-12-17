package com.alenma3apps.backendTastyMeal.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alenma3apps.backendTastyMeal.dto.request.ReportRequest;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.ReportModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IReportRepository;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe ReportService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {

    @Mock
    private IReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private UserModel user;
    private ReportRequest request;
    private CategoryModel category;
    private RecipeModel recipe;
    private ReportModel report;

    @BeforeEach
    public void init() {
        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        request = new ReportRequest();
        request.setReport("Insert report here.");

        category = new CategoryModel();
        category.setCategory("Vegan");

        recipe = new RecipeModel();
        recipe.setTitle("Title");
        recipe.setImageUrl("image_url");
        recipe.setDescription("Insert description");
        recipe.setCookingTime(20);
        recipe.setNumPersons(4);
        recipe.setIngredients("Add ingredients");
        recipe.setRecipeCategory(category);
        recipe.setOwnerId(user);

        report = new ReportModel();
        report.setReport("Insert report here.");
        report.setRecipe(new RecipeModel());
        report.setUser(user);
    }

    /**
     * Test per comprovar que es crea el report.
     * @author Albert Borras
     */
    @Test
    public void ReportServiceTests_createReport() {
        when(reportRepository.save(Mockito.any(ReportModel.class))).thenReturn(report);

        ReportModel result = reportService.createReport(request, user, recipe);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getReport()).isEqualTo(request.getReport());
    }

    /**
     * Test per comprovar que s'obté el llistat de reports de la recepta 
     * passada per paràmetre.
     * @author Albert Borras
     */
    @Test
    public void ReportServiceTests_getReports() {
        ArrayList<ReportModel> listReports = new ArrayList<ReportModel>();
        listReports.add(report);

        when(reportRepository.findByRecipe(recipe)).thenReturn(listReports);

        List<ReportModel> result = reportService.getReports(recipe);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'edita el report amb l'id passat per paràmetre. 
     * @author Albert Borras
     */
    @Test
    public void ReportServiceTests_editMyReport() {
        when(reportRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(report));

        ResponseEntity<?> result = reportService.editMyReport(1L, user, request);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'elimina el report el qual l'usuari que ho demana és l'autor.
     * @author Albert Borras
     */
    @Test
    public void ReportServiceTests_deleteMyReport() {
        when(reportRepository.findById((long) 1)).thenReturn(Optional.ofNullable(report));

        ResponseEntity<?> result = reportService.deleteMyReport((long) 1, user);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'elimina un report passat pel paràmetre id.
     * @author Albert Borras
     */
    @Test
    public void ReportServiceTests_deleteReportById() {
        when(reportRepository.findById((long) 1)).thenReturn(Optional.ofNullable(report));

        ResponseEntity<?> result = reportService.deleteReportById((long) 1);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
