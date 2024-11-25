package com.alenma3apps.backendTastyMeal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que conté les dades de resposta a la petició de validació de l'usuari.
 * @author Albert Borras
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {
    
    /**
     * Paràmetres retornats.
     */
    private String username;
    private boolean isValid;
    
}
