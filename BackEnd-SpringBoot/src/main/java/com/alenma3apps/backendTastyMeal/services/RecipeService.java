package com.alenma3apps.backendTastyMeal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.RecipeRequest;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;

@Service
public class RecipeService {
    @Autowired
    private IRecipeRepository recipeRepository;

    public RecipeModel createRecipe(RecipeRequest request, UserModel user) {
        RecipeModel newRecipe = new RecipeModel();
        newRecipe.setTitle(request.getTitle());
        newRecipe.setImageUrl(request.getImageUrl());
        newRecipe.setDescription(request.getDescription());
        newRecipe.setCookingTime(request.getCookingTime());
        newRecipe.setNumPersons(request.getNumPersons());
        newRecipe.setIngredients(request.getIngredients());
        newRecipe.setOwnerId(user);

        return recipeRepository.save(newRecipe);
    }

    public List<RecipeModel> getMyRecipes(Long id) {

       return recipeRepository.findByOwnerId(id);
    }

    public List<RecipeModel> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
