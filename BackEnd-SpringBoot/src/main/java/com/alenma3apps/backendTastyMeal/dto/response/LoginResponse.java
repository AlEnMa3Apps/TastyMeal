package com.alenma3apps.backendTastyMeal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que conté les dades de resposta a la petició d'inici de sessió.
 * @author Albert Borras
 */
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    /**
     * Paràmetres retornats.
     */
    private String token;
    private String role;

}
