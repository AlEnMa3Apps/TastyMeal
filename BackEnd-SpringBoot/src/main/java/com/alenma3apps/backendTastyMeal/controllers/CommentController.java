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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.request.CommentRequest;
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
 * Clase controladora que conté els endpoints de gestió de comentaris.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint per afegir un comentari a la recepta del paràmetre id.
     * @param header Capçalera de la petició http.
     * @param id Id de la recepta a afegir el comentari.
     * @param request El comentari.
     * @return El comentari afegit o en cas d'error retornarà un missatge d'estat.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @PostMapping("/recipe/{id}/comment")
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
    @GetMapping("/recipe/{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long id) {
        RecipeModel recipe = recipeService.getRecipeById(id);
        List<CommentModel> comments = commentService.getComments(recipe);

        if (comments != null) {
            return ResponseEntity.ok(comments);
        } else {
            return SpringResponse.commentsNotFound();
        }
    }

    /**
     * Endpoint per eliminar comentaris pel paràmetre id
     * que siguin de l'usuari que fa la petició.
     * @param header Capçalera de la petició http.
     * @param comment_id Id del comentari a eliminar.
     * @return Missatge notificant si s'ha eliminat o no el comentari.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'GESTOR')")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteMyComment(HttpServletRequest header, @PathVariable Long id) {
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

        return commentService.deleteMyComment(id, user);
    }

    /**
     * Endpoint per eliminar un comentari amb el paràmetre id.
     * @param comment_id Id del comentari a eliminar.
     * @return Missatge notificant si s'ha eliminat o no el comentari.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @DeleteMapping("/comment/a/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        return commentService.deleteCommentById(id);
    }
}