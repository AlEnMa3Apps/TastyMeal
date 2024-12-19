package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que conté les dades per les peticions de esdeveniments.
 * @author Albert Borras
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    /**
     * Paràmetres d'entrada per les peticions d'esdeveniments.
     */
    private String title;
    private String date;
    private Integer duration;
    private String description;
    private String eventCategory;
    
}
