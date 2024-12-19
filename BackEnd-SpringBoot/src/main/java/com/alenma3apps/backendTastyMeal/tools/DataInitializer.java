package com.alenma3apps.backendTastyMeal.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.alenma3apps.backendTastyMeal.models.CategoryModel;
import com.alenma3apps.backendTastyMeal.models.EventCategoryModel;
import com.alenma3apps.backendTastyMeal.models.EventModel;
import com.alenma3apps.backendTastyMeal.models.RecipeModel;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.ICategoryRepository;
import com.alenma3apps.backendTastyMeal.repositories.IEventCategoryRepository;
import com.alenma3apps.backendTastyMeal.repositories.IEventRepository;
import com.alenma3apps.backendTastyMeal.repositories.IRecipeRepository;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class DataInitializer {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRecipeRepository recipeRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IEventCategoryRepository eventCategoryRepository;

    @Autowired
    private IEventRepository eventRepository;
    
    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void initializer() throws IOException {
        if (userRepository.count() == 0){
            UserModel admin = new UserModel();
            admin.setUsername("EnricN");
            admin.setPassword(passwordEncoder.encode("root"));
            admin.setEmail("enric_admin@gmail.com");
            admin.setRole(RoleModel.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);

            UserModel user = new UserModel();
            user.setUsername("JohnDoe");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setEmail("john_doe@gmail.com");
            user.setRole(RoleModel.USER);
            user.setActive(true);
            userRepository.save(user);
        }

        if (categoryRepository.count() == 0) {
            String[] categories = {"Vegan", "Vegetarian", "Meat", "Fish", "Pasta", "Pizza", "Salad", "Dessert", "Drinks", "Breakfast", "Soup"};
            for (String categoryName : categories) {
                CategoryModel category = new CategoryModel();
                category.setCategory(categoryName);
                categoryRepository.save(category);
            }
        }

        if(recipeRepository.count()==0) {
            InputStream inputStream = resourceLoader.getResource("classpath:recetas.json").getInputStream();

            ObjectMapper mapper = new ObjectMapper();

            List<RecipeModel> recipes = Arrays.asList(mapper.readValue(inputStream, RecipeModel[].class));
            recipeRepository.saveAll(recipes);
        }

        if (eventCategoryRepository.count() == 0) {
            String[] categories = {"Mediterranean Recipes", "Asian Recipes", "American Recipes", "European Recipes", "Party Recipes", 
                "Seasonal Recipes", "Romantic Dinners", "Brunch", "Takeaway Food", "Ethnic Food", "Street Food"};
            for (String categoryName : categories) {
                EventCategoryModel category = new EventCategoryModel();
                category.setCategory(categoryName);
                eventCategoryRepository.save(category);
            }
        }

        if(eventRepository.count()==0) {
            InputStream inputStream = resourceLoader.getResource("classpath:eventos.json").getInputStream();

            ObjectMapper mapper = new ObjectMapper();

            List<EventModel> events = Arrays.asList(mapper.readValue(inputStream, EventModel[].class));
            eventRepository.saveAll(events);
        }

    }
}
