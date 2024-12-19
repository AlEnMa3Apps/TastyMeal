package com.alenma3apps.backendTastyMeal.tools;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;

public class SpringResponse {

    /**
     * @return CODE 401 Unauthorized
     */
    public static ResponseEntity<JsonResponse> invalidToken(){
        JsonResponse response = new JsonResponse(HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> userNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> userAlreadyExist(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_ALREADY_EXIST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> userDeleted(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "USER_DELETED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorDeletingUser(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_NOT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> userEdited(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "USER_EDITED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorEditingUser(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_USER_NOT_EDITED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 401 Unauthorized
     */
    public static ResponseEntity<JsonResponse> wrongPassword() {
        JsonResponse response = new JsonResponse(HttpStatus.UNAUTHORIZED.value(), "WRONG_PASSWORD");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> passwordChanged() {
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "PASSWORD_CHANGED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorChangingpassword() {
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_PASSWORD_NOT_CHANGED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> recipeCreated() {
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "RECIPE_CREATED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorCreatingRecipe(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_RECIPE_NOT_CREATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> recipeAlreadyExist(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "RECIPE_ALREADY_EXIST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> notOwnerRecipe(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "NOT_OWNER_RECIPE");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> recipeNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "RECIPE_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> recipeDeleted(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "RECIPE_DELETED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorDeletingRecipe(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_RECIPE_NOT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> recipeUpdated(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "RECIPE_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorUpdatingRecipe(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_RECIPE_NOT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorCommentNotCreated(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_COMMENT_NOT_CREATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> commentsNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "COMMENTS_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> commentNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "COMMENT_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> commentDeleted(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "COMMENT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorDeletingComment(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_COMMENT_NOT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> notAuthorComment(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "NOT_AUTHOR_COMMENT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> commentUpdated(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "COMMENT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorUpdatingComment(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_COMMENT_NOT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorReportNotCreated(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_REPORT_NOT_CREATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> reportsNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "REPORTS_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> reportNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "REPORT_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> reportDeleted(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "REPORT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorDeletingReport(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_REPORT_NOT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> notOwnerReport(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "NOT_OWNER_REPORT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> reportUpdated(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "REPORT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorUpdatingReport(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_REPORT_NOT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

     /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> favoriteRecipeSaved(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "FAVORITE_RECIPE_SAVED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> favoriteRecipeRemoved(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "FAVORITE_RECIPE_REMOVED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorRemovingFavoriteRecipe(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_REMOVING_FAVORITE_RECIPE");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> favoriteRecipeAlreadyExist(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "FAVORITE_RECIPE_ALREADY_EXIST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorSavingFavoriteRecipe(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_SAVING_FAVORITE_RECIPE");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorGettingFavoriteRecipes(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_GETTING_FAVORITE_RECIPES");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> favoriteRecipeNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "FAVORITE_RECIPE_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> eventCreated(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "EVENT_CREATED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorCreatingEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_CREATING_EVENT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> eventAlreadyExist(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "EVENT_ALREADY_EXIST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> eventNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "EVENT_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 404 Not Found
     */
    public static ResponseEntity<JsonResponse> eventsNotFound(){
        JsonResponse response = new JsonResponse(HttpStatus.NOT_FOUND.value(), "EVENTS_NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> eventUpdated(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "EVENT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorUpdatingEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_EVENT_NOT_UPDATED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> userRegisteredToEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "USER_REGISTERED_TO_EVENT");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> userAlreadyRegisteredToEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_ALREADY_REGISTERED_TO_EVENT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorRegisteringUserToEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_REGISTERING_USER_TO_EVENT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorUnregisteringUserToEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_UNREGISTERING_USER_TO_EVENT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> eventDeleted(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "EVENT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorDeletingEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_EVENT_NOT_DELETED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> errorGettingEventsRegistered(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "ERROR_GETTING_EVENTS_REGISTERED");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * @return CODE 200 OK
     */
    public static ResponseEntity<JsonResponse> userUnregisteredToEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.OK.value(), "USER_UNREGISTERED_TO_EVENT");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @return CODE 400 Bad Request
     */
    public static ResponseEntity<JsonResponse> userNotRegisteredToEvent(){
        JsonResponse response = new JsonResponse(HttpStatus.BAD_REQUEST.value(), "USER_NOT_REGISTERED_TO_EVENT");
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