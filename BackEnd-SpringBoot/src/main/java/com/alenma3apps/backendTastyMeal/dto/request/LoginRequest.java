package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que conté les dades per la petició d'inici de sessió.
 * @author Albert Borras
 */
@Getter @ Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * Paràmetres d'entrada de la petició per iniciar l'usuari.
     */
    private String username;
    private String password;
    
}