package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Petición de registro de usuario.
 * @author Albert Borras
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * Parámetros de entrada para la petición de registro de usuario.
     */
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

}