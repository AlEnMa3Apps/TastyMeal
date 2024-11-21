package com.alenma3apps.backendTastyMeal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {

    private String username;
    private boolean isValid;
    
}
