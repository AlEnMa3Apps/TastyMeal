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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe FavoriteRecipeService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class FavoriteRecipeServiceTests {

    @Mock
    IRecipeRepository recipeRepository;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    private FavoriteRecipeService favoriteRecipeService;

    private UserModel user;
    private RecipeModel recipe;
    private CategoryModel category;
    private List<RecipeModel> favoriteRecipes;
    private List<UserModel> users;

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

    }

    /**
     * Test per comprovar que es guarda la recepta com a preferida.
     * @author Albert Borras
     */
    @Test
    public void FavoriteRecipeServiceTests_saveFavoriteRecipe() {
        
        when(recipeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(recipe));

        ResponseEntity<?> result = favoriteRecipeService.saveFavoriteRecipe((long) 1, user);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    /**
     * Test per comprovar que s'obté el llistat de receptes preferides de l'usuari.
     * @author Albert Borras
     */
    @Test
    public void FavoriteRecipeServiceTests_getMyFavoriteRecipes() {

        ResponseEntity<?> result = favoriteRecipeService.getMyFavoriteRecipes(user);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    /**
     * Test per comprovar que s'elimina la recepta de les receptes preferides.
     * @author Albert Borras
     */
    @Test
    public void FavoriteRecipeServiceTests_deleteFavoriteRecipe() {

        when(recipeRepository.findById((long) 1)).thenReturn(Optional.ofNullable(recipe));

        favoriteRecipes.add(recipe);
        users.add(user);

        ResponseEntity<?> result = favoriteRecipeService.deleteFavoriteRecipe((long) 1, user);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        
    }

}
