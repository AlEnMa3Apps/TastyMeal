package com.alenma3apps.backendTastyMeal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

/**
 * Classe que gestiona la lògica de les receptes preferides.
 * @author Albert Borras
 */
@Service
public class FavoriteRecipeService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRecipeRepository recipeRepository;


    public ResponseEntity<?> saveFavoriteRecipe(Long recipeId, UserModel user) {
        Optional<RecipeModel> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            return SpringResponse.recipeNotFound();
        }

        RecipeModel recipe = recipeOptional.get();

        if (!user.getRecipesFavorite().contains(recipe)) {
            user.getRecipesFavorite().add(recipe);
            recipe.getUsername().add(user);

            userRepository.save(user);
            recipeRepository.save(recipe);
            return SpringResponse.favoriteRecipeSaved();
        } else {
            return SpringResponse.favoriteRecipeAlreadyExist();
        }

    }

    public ResponseEntity<?> getMyFavoriteRecipes(UserModel user) {
        List<Long> favoritesRecipesId = new ArrayList<>();
        user.getRecipesFavorite().forEach( it ->
                {
                    assert false;
                    favoritesRecipesId.add(it.getId());
                }
        );
        return ResponseEntity.ok(favoritesRecipesId);
    }

    public ResponseEntity<?> deleteFavoriteRecipe(Long recipeId, UserModel user) {
        Optional<RecipeModel> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            return SpringResponse.recipeNotFound();
        }

        RecipeModel recipe = recipeOptional.get();

        if (user.getRecipesFavorite().contains(recipe)) {
            user.getRecipesFavorite().remove(recipe);
            recipe.getUsername().remove(user);

            userRepository.save(user);
            recipeRepository.save(recipe);
            return SpringResponse.favoriteRecipeRemoved();
        } else {
            return SpringResponse.favoriteRecipeNotFound();
        }

    }
    
}
