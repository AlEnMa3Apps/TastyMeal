package com.alenma3apps.backendTastyMeal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.RecipeRequest;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.ICategoryRepository;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

@Service
public class RecipeService {
    @Autowired
    private IRecipeRepository recipeRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    /**
     * 
     * @param request
     * @param user
     * @return
     */
    public RecipeModel createRecipe(RecipeRequest request, UserModel user) {
        Optional<CategoryModel> categoryOptional = categoryRepository.findByCategory(request.getRecipeCategory());
        CategoryModel category = categoryOptional.get();

        RecipeModel newRecipe = new RecipeModel();
        newRecipe.setTitle(request.getTitle());
        newRecipe.setImageUrl(request.getImageUrl());
        newRecipe.setDescription(request.getDescription());
        newRecipe.setCookingTime(request.getCookingTime());
        newRecipe.setNumPersons(request.getNumPersons());
        newRecipe.setIngredients(request.getIngredients());
        newRecipe.setOwnerId(user);
        newRecipe.setRecipeCategory(category);

        return recipeRepository.save(newRecipe);
    }

    /**
     * 
     * @param user
     * @return
     */
    public List<RecipeModel> getMyRecipes(UserModel user) {
       return recipeRepository.findByOwnerId(user);
    }

    /**
     * 
     * @return
     */
    public List<RecipeModel> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * 
     * @param id
     * @return
     */
    public RecipeModel getRecipeById(Long id) {

        Optional<RecipeModel> recipeOptional = recipeRepository.findById(id);

        if(!recipeOptional.isPresent())
            return null;

        return recipeOptional.get();
    }

    /**
     * 
     * @param recipeId
     * @param user
     * @param request
     * @return
     */
    public ResponseEntity<?> editMyRecipe(Long recipeId, UserModel user, RecipeRequest request) {
        Optional<RecipeModel> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            return SpringResponse.recipeNotFound();
        }

        RecipeModel recipe = recipeOptional.get();
        if (recipe.getOwnerId() != user) {
            return SpringResponse.notOwnerRecipe();
        }

        Optional<CategoryModel> categoryOptional = categoryRepository.findByCategory(request.getRecipeCategory());
        CategoryModel category = categoryOptional.get();

        try {
            recipe.setTitle(request.getTitle());
            recipe.setDescription(request.getDescription());
            recipe.setCookingTime(request.getCookingTime());
            recipe.setImageUrl(recipe.getImageUrl());
            recipe.setNumPersons(request.getNumPersons());
            recipe.setIngredients(request.getIngredients());
            recipe.setRecipeCategory(category);
    
            recipeRepository.save(recipe);
            return SpringResponse.recipeUpdated();
        } catch (Exception ex) {
            return SpringResponse.errorUpdatingRecipe();
        }
    }

    /**
     * 
     * @param recipeId
     * @param user
     * @return
     */
    public ResponseEntity<?> deleteMyRecipe(Long recipeId, UserModel user) {
        Optional<RecipeModel> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            return SpringResponse.recipeNotFound();
        }

        RecipeModel recipe = recipeOptional.get();
        if (recipe.getOwnerId() != user) {
            return SpringResponse.notOwnerRecipe();
        } else {
            try {
                recipeRepository.delete(recipe);
                return SpringResponse.recipeDeleted();
            } catch (Exception ex) {
                return SpringResponse.errorDeletingRecipe();
            }   
        }  
    }
}
