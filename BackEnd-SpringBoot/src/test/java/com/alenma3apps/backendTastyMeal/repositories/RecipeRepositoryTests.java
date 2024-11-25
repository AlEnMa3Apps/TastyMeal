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
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;

/**
 * Classe test per testejar les funcions utilitzades de 
 * la interfície IRecipeRepository per interactuar amb 
 * la base de dades.
 * @author Albert Borras
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RecipeRepositoryTests {

    @Autowired
    private IRecipeRepository recipeRepository;
    @Autowired
    private IUserRepository userRepository;

    /**
     * Test per comprovar que la recepta es guarda a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void RecipeRepositoryTest_saveRecipe() {
        RecipeModel recipe = new RecipeModel();
        recipe.setCookingTime(20);
        recipe.setDescription("Insert description here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setIngredients("Insert ingredients here.");
        recipe.setNumPersons(4);
        recipe.setTitle("Insert title here.");

        RecipeModel savedRecipe = recipeRepository.save(recipe);

        Assertions.assertThat(savedRecipe).isNotNull();
        Assertions.assertThat(savedRecipe.getId()).isGreaterThan(0);
    }

    /**
     * Test per obtenir la recepta demanada per id a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void RecipeRepositoryTest_findRecipeById() {
        RecipeModel recipe = new RecipeModel();
        recipe.setCookingTime(20);
        recipe.setDescription("Insert description here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setIngredients("Insert ingredients here.");
        recipe.setNumPersons(4);
        recipe.setTitle("Insert title here.");
        recipeRepository.save(recipe);

        RecipeModel returnRecipe = recipeRepository.findById(recipe.getId()).get();
        Assertions.assertThat(returnRecipe).isNotNull();
    }

    /**
     * Test per comprovar que s'obtenen totes les receptes d'un usuari pel seu id.
     * @author Albert Borras
     */
    @Test
    public void RecipeRepositoryTest_findByOwnerId() {
        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");
        userRepository.save(user);

        RecipeModel recipe = new RecipeModel();
        recipe.setCookingTime(20);
        recipe.setDescription("Insert description here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setIngredients("Insert ingredients here.");
        recipe.setNumPersons(4);
        recipe.setTitle("Insert title here.");
        recipe.setOwnerId(user);
        recipeRepository.save(recipe);

        List<RecipeModel> returnListRecipes = recipeRepository.findByOwnerId(user);
        Assertions.assertThat(returnListRecipes).isNotNull();
        Assertions.assertThat(returnListRecipes.get(0).getOwnerId().getId()).isGreaterThan(0);
    }

    /**
     * Test per eliminar la recepta amb l'id passat pèr paràmetre a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void RecipeRepositoryTest_deleteRecipeById() {
        RecipeModel recipe = new RecipeModel();
        recipe.setCookingTime(20);
        recipe.setDescription("Insert description here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setIngredients("Insert ingredients here.");
        recipe.setNumPersons(4);
        recipe.setTitle("Insert title here.");
        recipeRepository.save(recipe);

        recipeRepository.deleteById(recipe.getId());

        Optional<RecipeModel> recipeReturn = recipeRepository.findById(recipe.getId());
        Assertions.assertThat(recipeReturn).isEmpty();
    }
}
