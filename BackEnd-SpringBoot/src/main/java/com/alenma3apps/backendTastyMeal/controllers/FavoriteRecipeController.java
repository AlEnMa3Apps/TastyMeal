package com.alenma3apps.backendTastyMeal.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.FavoriteRecipeService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controladora que conté els endpoints de gestió de receptes preferides.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api")
public class FavoriteRecipeController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private FavoriteRecipeService favoriteRecipeService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint que permet afegir com a preferida una recepta.
     * @param header Capçalera de la petició http.
     * @param id Id de la recepta.
     * @return Missatge notificant si s'ha desat com a preferida o no la recepta.
     */
    @PostMapping("recipe/{id}/favorite")
    public ResponseEntity<?> saveFavoriteRecipe(HttpServletRequest header, @PathVariable Long id) {
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

        ResponseEntity<?> response;

        try {
            response = favoriteRecipeService.saveFavoriteRecipe(id, user);
        } catch (Exception e) {
            response = SpringResponse.errorSavingFavoriteRecipe();
        }
        return response;

    }

    /**
     * Endpoint per obtenir les receptes preferides de l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @return Llistat de les receptes preferides.
     */
    @GetMapping("recipes/favorite")
    public ResponseEntity<?> getMyFavoriteRecipes(HttpServletRequest header) {
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

        ResponseEntity<?> response;

        try {
            response = favoriteRecipeService.getMyFavoriteRecipes(user);
        } catch (Exception e) {
            response = SpringResponse.errorGettingFavoriteRecipes();
        }
        return response;

    }

    /**
     * Endpoint que permet eliminar com a preferida una recepta.
     * @param header Capçalera de la petició http.
     * @param id Id de la recepta.
     * @return Missatge notificant si s'ha eliminat com a preferida o no la recepta.
     */
    @DeleteMapping("recipe/{id}/favorite")
    public ResponseEntity<?> deleteFavoriteRecipe(HttpServletRequest header, @PathVariable Long id) {
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

        ResponseEntity<?> response;

        try {
            response = favoriteRecipeService.deleteFavoriteRecipe(id, user);
        } catch (Exception e) {
            response = SpringResponse.errorSavingFavoriteRecipe();
        }
        return response;

    }

}
