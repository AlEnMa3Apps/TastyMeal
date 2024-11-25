package com.alenma3apps.backendTastyMeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * Classe que conté les dades per la petició de canvi de contrasenya.
 * @author Albert Borras
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {

    /**
     * Paràmetre d'entrada per la petició de canvi de contrasenya.
     */
    private String password;
}
