package com.alenma3apps.backendTastyMeal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Respuesta a la petición de inicio de sesión.
 * @author Albert Borras
 */
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    /**
     * Parámetros devueltos
     */
    private String token;
    private String role;

}
