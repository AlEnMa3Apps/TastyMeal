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

import com.alenma3apps.backendTastyMeal.dto.request.RecipeRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.CommentService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe RecipeController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = RecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RecipeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeService recipeService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IRecipeRepository recipeRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    private UserModel user;
    private RecipeRequest request;
    private CategoryModel category;
    private RecipeModel recipe;
    private List<RecipeModel> listRecipes;
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

        request = new RecipeRequest();
        request.setCookingTime(20);
        request.setDescription("Insert description here.");
        request.setImageUrl("Insert url image here.");
        request.setIngredients("Insert ingredients here.");
        request.setNumPersons(4);
        request.setTitle("Insert title here.");
        request.setRecipeCategory("Vegetarian");

        category = new CategoryModel();
        category.setId((long)1);
        category.setCategory(request.getRecipeCategory());

        recipe = new RecipeModel();
        recipe.setTitle(request.getTitle());
        recipe.setImageUrl(request.getImageUrl());
        recipe.setDescription(request.getDescription());
        recipe.setCookingTime(request.getCookingTime());
        recipe.setNumPersons(request.getNumPersons());
        recipe.setIngredients(request.getIngredients());
        recipe.setRecipeCategory(category);
        recipe.setOwnerId(user);

        listRecipes = new ArrayList<RecipeModel>();
        listRecipes.add(recipe);

        validationResponse = new ValidationResponse();
        validationResponse.setUsername("UserTest");
        validationResponse.setValid(true);
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per crear una recepta.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_createRecipe() throws Exception {

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(recipeService.createRecipe(ArgumentMatchers.any(RecipeRequest.class), ArgumentMatchers.any(UserModel.class))).willReturn(recipe);

        ResultActions response = mockMvc.perform(post("/api/recipe")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per obtenir totes les receptes de l'usuari
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_getMyRecipes() throws Exception {
        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));

        ResultActions response = mockMvc.perform(get("/api/recipes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(listRecipes)));

        response.andExpect(MockMvcResultMatchers.status().isOk());        
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per obtenir totes les receptes.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_getAllRecipes() throws Exception {
        
        ResultActions response = mockMvc.perform(get("/api/recipes/all")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(listRecipes)));

        response.andExpect(MockMvcResultMatchers.status().isOk());        
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per obtenir una recepta pel seu id 
     * passat per paràmetre.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_getRecipeById() throws Exception {
        given(recipeService.getRecipeById(ArgumentMatchers.any())).willReturn(recipe);
        
        ResultActions response = mockMvc.perform(get("/api/recipe/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)));

        response.andExpect(MockMvcResultMatchers.status().isOk());        
    }

     /**
     * Test per comprovar el funcionament
     * de l'endpoint per editar una recepta de l'usuari 
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_editMyRecipe() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "RECIPE_EDITED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(recipeService.editMyRecipe(1L, user, request)).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(put("/api/recipe/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isOk());        
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per eliminar una recepta de l'usuari 
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_deleteMyRecipe() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "RECIPE_DELETED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(recipeService.deleteMyRecipe(1L, user)).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/recipe/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isOk());        
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per eliminar una recepta pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void RecipeControllerTest_deleteRecipeById() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "RECIPE_DELETED"), HttpStatus.OK);

        given(recipeService.deleteRecipeById(1L)).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/recipe/a/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
