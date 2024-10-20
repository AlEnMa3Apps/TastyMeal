package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Petición de inicio de sesión de usuario.
 * @author Albert Borras
 */
@Getter @ Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * Parámetros de entrada de la petición para iniciar el usuario
     */
    private String username;
    private String password;
    
}