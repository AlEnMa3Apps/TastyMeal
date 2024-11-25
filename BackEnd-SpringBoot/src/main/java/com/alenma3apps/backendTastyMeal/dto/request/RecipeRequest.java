package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que conté les dades per les peticions de receptes.
 * @author Albert Borras
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {

    /**
     * Paràmetres d'entrada per les peticions de receptes.
     */
    private String title;
    private String imageUrl;
    private String description;
    private Integer cookingTime;
    private Integer numPersons;
    private String ingredients;
    private String recipeCategory;
}