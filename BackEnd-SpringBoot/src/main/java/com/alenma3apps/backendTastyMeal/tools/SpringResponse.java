package com.alenma3apps.backendTastyMeal.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

public class SpringResponse {

    public static ResponseEntity<JsonResponse> invalidToken(){
        JsonResponse response = new JsonResponse(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<JsonResponse> userNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.UNAUTHORIZED.value(), "USER_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<JsonResponse> userAlreadyExist(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_ALREADY_EXIST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<JsonResponse> userDeleted(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "USER_DELETED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<JsonResponse> errorDeletingUser(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_NOT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<JsonResponse> userEdited(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "USER_EDITED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<JsonResponse> errorEditingUser(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_USER_NOT_EDITED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<JsonResponse> wrongPassword() {
        JsonResponse response = new JsonResponse(HttpStatus.UNAUTHORIZED.value(), "WRONG_PASSWORD");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<JsonResponse> passwordChanged() {
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "PASSWORD_CHANGED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<JsonResponse> errorChangingpassword() {
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_PASSWORD_NOT_CHANGED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Getter @Setter
    public static class JsonResponse {
        private int status;
        private String code;

        public JsonResponse(int status, String code) {
            this.status = status;
            this.code = code;
        }
    }

}
