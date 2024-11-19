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
     * Registra la recepta passada per paràmetre a la base de dades.
     * @param request Recepta a registrar. 
     * @param user Usuari que ha creat la recepta.
     * @return Recepta registrada a la base de dades.
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
     * Retorna el llistat de les receptes de l'usuari passat per paràmetre.
     * @param user Usuari del qual es volen obtenir les receptes.
     * @return Llistat de totes les receptes de l'usuari.
     */
    public List<RecipeModel> getMyRecipes(UserModel user) {
       return recipeRepository.findByOwnerId(user);
    }

    /**
     * Retorna el llistat de totes les receptes.
     * @return Llistat de totes les receptes.
     */
    public List<RecipeModel> getAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Retorna la recepta passada per el paràmetre id.
     * @param id Id de la recepta.
     * @return Recepta si n'hi ha, del contrari retorna null.
     */
    public RecipeModel getRecipeById(Long id) {

        Optional<RecipeModel> recipeOptional = recipeRepository.findById(id);

        if(!recipeOptional.isPresent())
            return null;

        return recipeOptional.get();
    }

    /**
     * Edita la recepta passada pel paràmetre id.
     * @param recipeId Id de la recepta a editar.
     * @param user Usuari que edita la recepta.
     * @param request Paràmetres de la recepta a editar.
     * @return Missatge confirmant si s'ha editat o no la recepta.
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
     * Elimina una recepta passada pel paràmetre id que sigui de l'usuari que ho demana.
     * @param recipeId Id de la recepta a eliminar.
     * @param user Usuari que ho demana.
     * @return Missatge confirmant si s'ha eliminat o no la recepta.
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

    /**
     * Elimina una recepta passada pel paràmetre id.
     * @param recipeId Id de la recepta a eliminar.
     * @return Missatge confirmant si s'ha eliminat o no la recepta.
     */
    public ResponseEntity<?> deleteRecipeById(Long recipeId) {
        Optional<RecipeModel> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()){
            return SpringResponse.recipeNotFound();
        }

        RecipeModel recipe = recipeOptional.get();
        
        try {
            recipeRepository.delete(recipe);
            return SpringResponse.recipeDeleted();
        } catch (Exception ex) {
            return SpringResponse.errorDeletingRecipe();
        }   
    }

    /**
     * Comprova pel títol de la recepta si existeix a la base de dades.
     * @param request Recepta a comprovar
     * @return True si existeix, del contrari retorna false.
     * @author Albert Borras
     */
    public Boolean recipeExists(RecipeRequest request) {
        Optional<RecipeModel> checkRecipe = recipeRepository.findByTitle(request.getTitle());
        if (checkRecipe.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
