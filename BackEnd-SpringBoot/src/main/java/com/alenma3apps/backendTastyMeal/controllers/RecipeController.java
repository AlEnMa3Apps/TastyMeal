package com.alenma3apps.backendTastyMeal.controllers;

import java.util.ArrayList;
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

import com.alenma3apps.backendTastyMeal.dto.request.CommentRequest;
import com.alenma3apps.backendTastyMeal.dto.request.RecipeRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.CommentService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase controladora que conté els endpoints de gestió de receptes.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint per crear una recepta.
     * @param request Paràmetres de la nova recepta.
     * @param header Capçalera de la petició http.
     * @return Codi de la petició i estat de la petició.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PostMapping("/recipe")
    public ResponseEntity<?> createRecipe(@RequestBody RecipeRequest request, HttpServletRequest header) {
        if (recipeService.recipeExists(request)) {
            return SpringResponse.recipeAlreadyExist();
        }

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
     * Endpoint per obtenir el llistat de receptes de 
     * l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @return Llistat de totes les receptes de l'usuari que fa la petició.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
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
     * @return Llistat de totes les receptes.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("/recipes/all")
    public ResponseEntity<?> getAllRecipes() {
        List<RecipeModel> listRecipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(listRecipes);
    }

    /**
     * Endpoint per obtenir una recepta passada pel paràmetre id.
     * @param id Id de la recepta.
     * @return Recepta sol·licitada.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
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
     * Endpoint per editar una recepta passada per id 
     * que sigui de l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @param request Paràmetres per editar la recepta.
     * @param id Id de la recepta a editar.
     * @return Missatge notificant si s'ha editat o no la recepta.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
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
     * Endpoint per eliminar receptes pel paràmetre id
     * que siguin de l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @param id Id de la recepta a eliminar.
     * @return Missatge notificant si s'ha eliminat o no la recepta.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @DeleteMapping("recipe/{id}")
    public ResponseEntity<?> deleteMyRecipe(HttpServletRequest header, @PathVariable Long id) {
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

    /**
     * Endpoint per eliminar qualsevol recepta pel paràmetre id.
     * @param id Id de la recepta a eliminar.
     * @return Missatge notificant si s'ha eliminat o no la recepta.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("recipe/a/{id}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable Long id) {
        return recipeService.deleteRecipeById(id);
    }

    /**
     * Endpoint per afegir un comentari a la recepta del paràmetre id.
     * @param header Capçalera de la petició http.
     * @param id Id de la recepta a afegir el comentari.
     * @param request El comentari.
     * @return El comentari afegit o en cas d'error retornarà un missatge d'estat.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PostMapping("recipe/{id}/comment")
    public ResponseEntity<?> addComment(HttpServletRequest header, @PathVariable Long id, @RequestBody CommentRequest request) {
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

        RecipeModel recipe = recipeService.getRecipeById(id);

        CommentModel comment = commentService.createComment(request, user, recipe);

        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return SpringResponse.errorCommentNotCreated();
        }
    }
    
    /**
     * Endpoint per obtenir els comentaris de la recepta del paràmetre id.
     * @param id Id de la recepta a obtenir els comentaris.
     * @return Llistat dels comentaris que te la recepta.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @GetMapping("recipe/{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long id) {
        RecipeModel recipe = recipeService.getRecipeById(id);
        List<CommentModel> comments = commentService.getComments(recipe);

        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return SpringResponse.commentsNotFound();
        }
    }

}