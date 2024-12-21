package com.alenma3apps.backendTastyMeal.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.FavoriteRecipeService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse.JsonResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe FavoriteRecipeController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = FavoriteRecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class FavoriteRecipeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeService recipeService;

    @MockBean
    private FavoriteRecipeService favoriteRecipeService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IRecipeRepository recipeRepository;

    private UserModel user;
    private RecipeModel recipe;
    private CategoryModel category;
    private List<RecipeModel> favoriteRecipes;
    private List<UserModel> users;
    private ValidationResponse validationResponse;

    @BeforeEach
    public void init() {
        favoriteRecipes = new ArrayList<RecipeModel>();
        users = new ArrayList<UserModel>();

        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");
        user.setRecipesFavorite(favoriteRecipes);

        category = new CategoryModel();
        category.setCategory("Vegetarian");

        recipe = new RecipeModel();
        recipe.setTitle("Insert title here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setDescription("Insert description here.");
        recipe.setCookingTime(20);
        recipe.setNumPersons(4);
        recipe.setIngredients("Insert ingredients here.");
        recipe.setRecipeCategory(category);
        recipe.setOwnerId(user);
        recipe.setUsername(users);

        validationResponse = new ValidationResponse();
        validationResponse.setUsername("UserTest");
        validationResponse.setValid(true);
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per guardar una recepta com a preferida.
     * @throws Exception
     */
    @Test
    public void FavoriteRecipeControllerTests_saveFavoriteRecipe() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "FAVORITE_RECIPE_SAVED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername(ArgumentMatchers.anyString())).willReturn(Optional.ofNullable(user));
        given(favoriteRecipeService.saveFavoriteRecipe(ArgumentMatchers.anyLong(), ArgumentMatchers.any(UserModel.class))).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(post("/api/recipe/1/favorite"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament de l'endpoint per obteni les receptes preferides de l'usuari.
     * @throws Exception
     */
    @Test
    public void FavoriteRecipeControllerTests_getMyFavoriteRecipe() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "OK"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(favoriteRecipeService.getMyFavoriteRecipes(ArgumentMatchers.any(UserModel.class))).willAnswer(invocation -> responseExpect);
        
        ResultActions response = mockMvc.perform(get("/api/recipes/favorite"));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    /**
     * Test per comprovar el funcionament de l'endpoint per eliminar una recepta de les receptes preferides.
     * @throws Exception
     */
    @Test
    public void FavoriteRecipeControllerTests_deleteFavoriteRecipe() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "FAVORITE_RECIPE_REMOVED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(favoriteRecipeService.deleteFavoriteRecipe(ArgumentMatchers.anyLong(), ArgumentMatchers.any(UserModel.class))).willAnswer(invocation -> responseExpect);
        
        ResultActions response = mockMvc.perform(delete("/api/recipe/1/favorite"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
