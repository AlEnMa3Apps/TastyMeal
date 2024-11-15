package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    private String title;
    private String imageUrl;
    private String description;
    private Integer cookingTime;
    private Integer numPersons;
    private String ingredients;
    private String recipeCategory;
}