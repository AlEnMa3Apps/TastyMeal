package com.alenma3apps.backendTastyMeal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.CommentRequest;
import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.ICommentRepository;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

/**
 * Classe que gestiona la lògica dels comentaris.
 * @author Albert Borras
 */
@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;

    /**
     * Mètode per crear un comentari en una recepta.
     * @param request comentari.
     * @param user usuari que crea el comentari.
     * @param recipe recepta on va dirigida el comentari.
     * @return comentari creat.
     * @author Albert Borras
     */
    public CommentModel createComment(CommentRequest request, UserModel user, RecipeModel recipe) {
        CommentModel newComment = new CommentModel();
        newComment.setAuthor(user.getUsername());
        newComment.setComment(request.getComment());
        newComment.setRecipe(recipe);
        newComment.setUser(user);

        CommentModel commentSaved = commentRepository.save(newComment);

        return commentSaved;
    }

    /**
     * Mètode per obtenir el llistat de comentaris de la recepta passada per paràmetre.
     * @param recipe Recepta la qual obtenir els seus comentaris.
     * @return Llistat de comentaris que tingui la recepta.
     * @author Albert Borras
     */
    public List<CommentModel> getComments(RecipeModel recipe) {
        List<CommentModel> comments;
        comments = commentRepository.findByRecipe(recipe);
        return comments;
    }

    /**
     * Elimina un comentari passat pel paràmetre id que sigui de l'usuari que ho demana.
     * @param commentId Id del comentari a eliminar.
     * @param user Usuari que ho demana.
     * @return Missatge confirmant si s'ha eliminat o no el comentari.
     */
    public ResponseEntity<?> deleteMyComment(Long commentId, UserModel user) {
        Optional<CommentModel> commentOptional = commentRepository.findById(commentId);
        if (!commentOptional.isPresent()){
            return SpringResponse.commentNotFound();
        }

        CommentModel comment = commentOptional.get();
        if (comment.getUser() != user) {
            return SpringResponse.notAuthorComment();
        } else {
            try {
                commentRepository.delete(comment);
                return SpringResponse.commentDeleted();
            } catch (Exception ex) {
                return SpringResponse.errorDeletingComment();
            }   
        }  
    }

    /**
     * Elimina un comentari passat pel paràmetre id que sigui de l'usuari que ho demana.
     * @param commentId Id del comentari a eliminar.
     * @return Missatge confirmant si s'ha eliminat o no el comentari.
     */
    public ResponseEntity<?> deleteCommentById(Long commentId) {
        Optional<CommentModel> commentOptional = commentRepository.findById(commentId);
        if (!commentOptional.isPresent()){
            return SpringResponse.commentNotFound();
        }

        CommentModel comment = commentOptional.get();
        
        try {
            commentRepository.delete(comment);
            return SpringResponse.commentDeleted();
        } catch (Exception ex) {
            return SpringResponse.errorDeletingComment();
        }
    }
}