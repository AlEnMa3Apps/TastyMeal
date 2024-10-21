package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que conté les dades per la petició de registre d'usuari.
 * @author Albert Borras
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * Paràmetres d'entrada per la petició de registre d'usuari.
     */
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

}