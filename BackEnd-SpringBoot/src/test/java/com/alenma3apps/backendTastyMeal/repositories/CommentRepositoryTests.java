package com.alenma3apps.backendTastyMeal.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;

/**
 * Classe test per testejar les funcions utilitzades de 
 * la interfície ICommentRepository per interactuar amb 
 * la base de dades.
 * @author Albert Borras
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CommentRepositoryTests {

    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IRecipeRepository recipeRepository;

    /**
     * Test per comprovar que el comentari es guarda a la base de dades.
     * @author Albert Borras
     */
    @Test
    public void CommentRepositoryTest_saveComment() {
        CommentModel comment = new CommentModel();
        comment.setAuthor("UserTest");
        comment.setComment("Una molt bona recepta per provar.");
        comment.setRecipe(new RecipeModel());
        comment.setUser(new UserModel());

        CommentModel savedComment = commentRepository.save(comment);

        Assertions.assertThat(savedComment).isNotNull();
        Assertions.assertThat(savedComment.getId()).isGreaterThan(0);
    }

    /**
     * Test per comprovar que s'obté el llistat de comentaris de la recepta 
     * passada per paràmetre de la base de dades.
     * @author Albert Borras
     */
    @Test
    public void CommentRepositoryTest_findByRecipe() {
        RecipeModel recipe = new RecipeModel();
        recipe.setCookingTime(20);
        recipe.setDescription("Insert description here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setIngredients("Insert ingredients here.");
        recipe.setNumPersons(4);
        recipe.setTitle("Insert title here.");
        recipeRepository.save(recipe);

        List<CommentModel> returnListComments = commentRepository.findByRecipe(
            recipe);

            Assertions.assertThat(returnListComments).isNotNull();
    }

    /**
     * Test per comprovar que s'obté el comentari amb l'id passat per paràmetre de la base de dades.
     * @author Albert Borras
     */
    @Test
    public void CommentRepositoryTest_findById() {
        CommentModel comment = new CommentModel();
        comment.setAuthor("UserTest");
        comment.setComment("Una molt bona recepta per provar.");
        comment.setRecipe(new RecipeModel());
        comment.setUser(new UserModel());
        commentRepository.save(comment);

        Optional<CommentModel> returnedComment = commentRepository.findById(comment.getId());

        Assertions.assertThat(returnedComment).isNotNull();
        Assertions.assertThat(returnedComment.get().getId()).isGreaterThan(0);
    }

    /**
     * Test per comprovar que s'elimina un comentari de la base de dades.
     * @author Albert Borras
     */
    @Test
    public void CommentRepositoryTest_delete() {
        CommentModel comment = new CommentModel();
        comment.setAuthor("UserTest");
        comment.setComment("Una molt bona recepta per provar.");
        comment.setRecipe(new RecipeModel());
        comment.setUser(new UserModel());
        commentRepository.save(comment);

        commentRepository.delete(comment);

        Optional<CommentModel> commentReturn = commentRepository.findById(comment.getId());
        Assertions.assertThat(commentReturn).isEmpty();
    }

}
