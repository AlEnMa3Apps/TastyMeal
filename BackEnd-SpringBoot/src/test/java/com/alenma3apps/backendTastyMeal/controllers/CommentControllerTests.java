package com.alenma3apps.backendTastyMeal.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alenma3apps.backendTastyMeal.dto.request.CommentRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.CommentModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.ICommentRepository;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.CommentService;
import com.alenma3apps.backendTastyMeal.services.RecipeService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe CommentController.
 * @author Albert Borras
 */
@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CommentControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeService recipeService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IRecipeRepository recipeRepository;

    @MockBean
    private ICommentRepository commentRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    private UserModel user;
    private CommentRequest request;
    private CommentModel comment;
    private CategoryModel category;
    private RecipeModel recipe;
    private List<CommentModel> listComments;
    private ValidationResponse validationResponse;

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
        request.setComment("Insert comment here");

        category = new CategoryModel();
        category.setId((long) 1);
        category.setCategory("Vegetarian");

        recipe = new RecipeModel();
        recipe.setTitle("Insert title here.");
        recipe.setImageUrl("Insert url image here.");
        recipe.setDescription("Insert description here.");
        recipe.setCookingTime(20);
        recipe.setNumPersons(4);
        recipe.setIngredients("Insert ingredients here.");
        recipe.setOwnerId(user);
        recipe.setRecipeCategory(category);

        comment = new CommentModel();
        comment.setAuthor(user.getUsername());
        comment.setComment("Insert comment here");
        comment.setRecipe(recipe);
        comment.setUser(user);


        listComments = new ArrayList<CommentModel>();
        listComments.add(comment);

        validationResponse = new ValidationResponse();
        validationResponse.setUsername("UserTest");
        validationResponse.setValid(true);
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per crear un comentari.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void CommentControllerTests_addComment() throws Exception {
        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(recipeService.getRecipeById((long) 1)).willReturn(recipe);
        given(commentService.createComment(
            ArgumentMatchers.any(CommentRequest.class), 
            ArgumentMatchers.any(UserModel.class), 
            ArgumentMatchers.any(RecipeModel.class))
            ).willReturn(comment);

        ResultActions response = mockMvc.perform(post("/api/recipe/1/comment")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(comment)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per obtenir tots els comentaris d'una recepta.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void CommentControllerTests_getComments() throws Exception {
        given(recipeService.getRecipeById(ArgumentMatchers.anyLong())).willReturn(recipe);
        given(commentService.getComments(recipe)).willReturn(listComments);
        
        ResultActions response = mockMvc.perform(get("/api/recipe/1/comments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(listComments)));

        response.andExpect(MockMvcResultMatchers.status().isOk()); 
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per editar un comentari de l'usuari 
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void CommentControllerTests_editMyComment() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "COMMENT_EDITED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(commentService.editMyComment(
            ArgumentMatchers.anyLong(),
            ArgumentMatchers.any(UserModel.class),
            ArgumentMatchers.any(CommentRequest.class))
            ).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(put("/api/comment/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(comment)));
    
        response.andExpect(MockMvcResultMatchers.status().isOk());  
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per eliminar un comentari de l'usuari 
     * que fa la petició.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void CommentControllerTests_deleteMyComment()  throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "COMMENT_EDITED"), HttpStatus.OK);

        given(jwtService.validateTokenAndUser(ArgumentMatchers.any(HttpServletRequest.class))).willReturn(validationResponse);
        given(userRepository.findByUsername("UserTest")).willReturn(Optional.ofNullable(user));
        given(commentService.deleteMyComment(
            ArgumentMatchers.anyLong(), 
            ArgumentMatchers.any(UserModel.class))
        ).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/comment/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isOk()); 
    }

    /**
     * Test per comprovar el funcionament
     * de l'endpoint per eliminar un comentari pel seu id.
     * @throws Exception
     * @author Albert Borras
     */
    @Test
    public void CommentControllerTests_deleteCommentById() throws Exception {
        ResponseEntity<JsonResponse> responseExpect = new ResponseEntity<>(new JsonResponse(HttpStatus.OK.value(), "COMMENT_DELETED"), HttpStatus.OK);

        given(commentService.deleteCommentById(ArgumentMatchers.anyLong())).willAnswer(invocation -> responseExpect);

        ResultActions response = mockMvc.perform(delete("/api/comment/a/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
