package com.alenma3apps.backendTastyMeal.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alenma3apps.backendTastyMeal.dto.request.RecipeRequest;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.ICategoryRepository;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe RecipeService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTests {

    @Mock
    IRecipeRepository recipeRepository;

    @Mock
    ICategoryRepository categoryRepository;
    
    @InjectMocks
    private RecipeService recipeService;


    private UserModel user;
    private RecipeRequest request;
    private CategoryModel category;
    private RecipeModel savedRecipe;


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
        category.setCategory(request.getRecipeCategory());

        savedRecipe = new RecipeModel();
        savedRecipe.setTitle(request.getTitle());
        savedRecipe.setImageUrl(request.getImageUrl());
        savedRecipe.setDescription(request.getDescription());
        savedRecipe.setCookingTime(request.getCookingTime());
        savedRecipe.setNumPersons(request.getNumPersons());
        savedRecipe.setIngredients(request.getIngredients());
        savedRecipe.setRecipeCategory(category);
        savedRecipe.setOwnerId(user);
    }

    /**
     * Test per comprovar que es crea la recepta.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_createRecipe() {

        when(categoryRepository.findByCategory(Mockito.any(String.class))).thenReturn(Optional.ofNullable(category));
        when(recipeRepository.save(Mockito.any(RecipeModel.class))).thenReturn(savedRecipe);

        RecipeModel result = recipeService.createRecipe(request, user);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'obté el llistat de totes les receptes
     * de l'usuari que ho ha demanat.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_getMyRecipes() {
        ArrayList<RecipeModel> listRecipes = new ArrayList<RecipeModel>();
        listRecipes.add(savedRecipe);

        when(recipeRepository.findByOwnerId(Mockito.any(UserModel.class))).thenReturn(listRecipes);

        List<RecipeModel> result = recipeService.getMyRecipes(user);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'obté el llistat de totes les receptes.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_getAllRecipes() {
        ArrayList<RecipeModel> listRecipes = new ArrayList<RecipeModel>();
        listRecipes.add(savedRecipe);

        when(recipeRepository.findAll()).thenReturn(listRecipes);

        List<RecipeModel> result = recipeService.getAllRecipes();
        
        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'obté la recepta amb l'id passat 
     * per paràmetre.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_getRecipeById() {
        when(recipeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(savedRecipe));

        RecipeModel result = recipeService.getRecipeById((long) 1);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'edita la recepta.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_editMyRecipe() {
        when(categoryRepository.findByCategory(Mockito.any(String.class))).thenReturn(Optional.ofNullable(category));
        when(recipeRepository.findById(1L)).thenReturn(Optional.ofNullable(savedRecipe));

        ResponseEntity<?> result = recipeService.editMyRecipe(1L, user, request);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'elimina una recepta de l'usuari que ho demana.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_deleteMyRecipe() {
        when(recipeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(savedRecipe));

        ResponseEntity<?> result = recipeService.deleteMyRecipe((long) 1, user);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per eliminar una recepta amb l'id passat per paràmetre.
     * @author Albert Borras
     */
    @Test
    public void RecipeServiceTest_deleteRecipeById() {
        when(recipeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(savedRecipe));

        ResponseEntity<?> result = recipeService.deleteRecipeById((long) 1);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}