package com.alenma3apps.backendTastyMeal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ValidationResponse {

    private String username;
    private boolean isValid;
    
}
