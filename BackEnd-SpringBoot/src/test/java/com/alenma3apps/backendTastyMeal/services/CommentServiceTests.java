package com.alenma3apps.backendTastyMeal.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alenma3apps.backendTastyMeal.dto.request.CommentRequest;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.ICommentRepository;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe CommentService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    @Mock
    private ICommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private UserModel user;
    private CommentRequest request;
    private CategoryModel category;
    private RecipeModel recipe;
    private CommentModel comment;
    

    @BeforeEach
    public void init() {
        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        request = new CommentRequest();
        request.setComment("Insert comment here.");

        category = new CategoryModel();
        category.setCategory("Vegan");

        recipe = new RecipeModel();
        recipe.setTitle("Title");
        recipe.setImageUrl("image_url");
        recipe.setDescription("Insert description");
        recipe.setCookingTime(20);
        recipe.setNumPersons(4);
        recipe.setIngredients("Add ingredients");
        recipe.setRecipeCategory(category);
        recipe.setOwnerId(user);

        comment = new CommentModel();
        comment.setAuthor("UserTest");
        comment.setComment("Insert comment here.");
        comment.setRecipe(new RecipeModel());
        comment.setUser(user);
    }

    /**
     * Test per comprovar que es crea el comentari.
     * @author Albert Borras
     */
    @Test
    public void CommentServiceTests_createComment() {
        when(commentRepository.save(Mockito.any(CommentModel.class))).thenReturn(comment);

        CommentModel result = commentService.createComment(request, user, recipe);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getComment()).isEqualTo(request.getComment());
    }

    /**
     * Test per comprovar que s'obté el llistat de comentaris de la recepta 
     * passada per paràmetre.
     * @author Albert Borras
     */
    @Test
    public void CommentServiceTests_getComments() {
        ArrayList<CommentModel> listComments = new ArrayList<CommentModel>();
        listComments.add(comment);

        when(commentRepository.findByRecipe(recipe)).thenReturn(listComments);

        List<CommentModel> result = commentService.getComments(recipe);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void CommentServiceTests_editMyComment() {
        
    }

    /**
     * Test per comprovar que s'elimina un comentari el qual l'usuari que ho demana és l'autor.
     * @author Albert Borras
     */
    @Test
    public void CommentServiceTests_deleteMyComment() {
        when(commentRepository.findById((long) 1)).thenReturn(Optional.ofNullable(comment));

        ResponseEntity<?> result = commentService.deleteMyComment((long) 1, user);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    /**
     * Test per comprovar que s'elimina un comentari passat pel paràmetre id.
     * @author Albert Borras
     */
    @Test
    public void CommentServiceTests_deleteCommentById() {
        when(commentRepository.findById((long) 1)).thenReturn(Optional.ofNullable(comment));

        ResponseEntity<?> result = commentService.deleteCommentById((long) 1);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
}