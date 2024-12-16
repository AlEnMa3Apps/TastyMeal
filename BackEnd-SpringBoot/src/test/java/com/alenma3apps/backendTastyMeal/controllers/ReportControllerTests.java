package com.alenma3apps.backendTastyMeal.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alenma3apps.backendTastyMeal.dto.request.CommentRequest;
import com.alenma3apps.backendTastyMeal.dto.request.ReportRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.ReportModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IReportRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.services.ReportService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe ReportController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReportControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeService recipeService;

    @MockBean
    private ReportService reportService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IRecipeRepository recipeRepository;

    @MockBean
    private IReportRepository reportRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    private UserModel user;
    private ReportRequest request;
    private ReportModel report;
    private CategoryModel category;
    private RecipeModel recipe;
    private List<ReportModel> listReports;
    private ValidationResponse validationResponse;

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
        request.setReport("Insert report here");

        category = new CategoryModel();
        category.setCategory("Vegetarian");

        recipe = new RecipeModel();
        recipe.setTitle("Insert title here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setDescription("Insert description here.");
        recipe.setCookingTime(20);
        recipe.setNumPersons(4);
        recipe.setIngredients("Insert ingredients here.");
        recipe.setOwnerId(user);
        recipe.setRecipeCategory(category);

        report = new ReportModel();
        report.setReport("Insert report here");
        report.setRecipe(recipe);
        report.setUser(user);

        listReports = new ArrayList<ReportModel>();
        listReports.add(report);

        validationResponse = new ValidationResponse();
        validationResponse.setUsername("UserTest");
        validationResponse.setValid(true);
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per crear un report.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void ReportControllerTests_addReport() throws Exception {
        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(recipeService.getRecipeById((long) 1)).willReturn(recipe);
        given(reportService.createReport(
            ArgumentMatchers.any(ReportRequest.class), 
            ArgumentMatchers.any(UserModel.class), 
            ArgumentMatchers.any(RecipeModel.class))
            ).willReturn(report);

        ResultActions response = mockMvc.perform(post("/api/recipe/1/report")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(report)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per obtenir tots els reports d'una recepta.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void ReportControllerTests_getReports() throws Exception {
        given(recipeService.getRecipeById(ArgumentMatchers.anyLong())).willReturn(recipe);
        given(reportService.getReports(recipe)).willReturn(listReports);
        
        ResultActions response = mockMvc.perform(get("/api/recipe/1/reports")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(listReports)));

        response.andExpect(MockMvcResultMatchers.status().isOk()); 
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per editar un report de l'usuari 
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void ReportControllerTests_editMyReport() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "REPORT_EDITED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(reportService.editMyReport(
            ArgumentMatchers.anyLong(),
            ArgumentMatchers.any(UserModel.class),
            ArgumentMatchers.any(ReportRequest.class))
            ).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(put("/api/report/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(report)));
    
        response.andExpect(MockMvcResultMatchers.status().isOk());  
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per eliminar un report de l'usuari 
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void ReportControllerTests_deleteMyReport()  throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "REPORT_EDITED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(reportService.deleteMyReport(
            ArgumentMatchers.anyLong(), 
            ArgumentMatchers.any(UserModel.class))
        ).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/report/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(report)));

        response.andExpect(MockMvcResultMatchers.status().isOk()); 
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per eliminar un report pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void ReportControllerTests_deleteReportById() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "REPORT_DELETED"), HttpStatus.OK);

        given(reportService.deleteReportById(ArgumentMatchers.anyLong())).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/report/a/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(report)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
