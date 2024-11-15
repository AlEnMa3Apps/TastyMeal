package com.alenma3apps.backendTastyMeal.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.request.RecipeRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint per crear una recepta
     * @param request Paràmetres de la nova recepta
     * @param header capçalera de la petició http
     * @return Codi de la petició i estat de la petició.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/recipe")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequest request, HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String username = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
           return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        RecipeModel recipe = recipeService.createRecipe(request, user);

        if (recipe != null) {
            return SpringResponse.recipeCreated();
        } else {
            return SpringResponse.errorCreatingRecipe();
        }
    }

    /**
     * Endpoint per obtenir el llistat de receptes de l'usuari que fa la petició.
     * @param header
     * @return
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/recipes")
    public ResponseEntity<?> getMyRecipes(HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String username = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
           return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();
        List<RecipeModel> listRecipes = recipeService.getMyRecipes(user);
        return ResponseEntity.ok(listRecipes);
    }

    /**
     * Endpoint per obtenir totes les receptes de tots els usuaris.
     * @return Llistat de receptes.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/recipes/all")
    public ResponseEntity<?> getAllRecipes() {
        List<RecipeModel> listRecipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(listRecipes);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/recipe/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable("id") Long id) {
        RecipeModel recipe = recipeService.getRecipeById(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        } else {
            return SpringResponse.recipeNotFound();
        }
    }

    /**
     * 
     * @param header
     * @param request
     * @param id
     * @return
     */
    @PutMapping("recipe/{id}")
    public ResponseEntity<?> editMyRecipe(HttpServletRequest header, @RequestBody RecipeRequest request, @PathVariable Long id) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();
        return recipeService.editMyRecipe(id, user, request);
    }

    /**
     * 
     * @param header
     * @param id
     * @return
     */
    @DeleteMapping("recipe/{id}")
    public ResponseEntity<?> deleteRecipe(HttpServletRequest header, @PathVariable Long id) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);
        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String userName = validationResponse.getUsername();
        Optional<UserModel> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isEmpty()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();

        return recipeService.deleteMyRecipe(id, user);
    }
}
