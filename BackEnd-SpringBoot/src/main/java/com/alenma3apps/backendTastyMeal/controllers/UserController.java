package com.alenma3apps.backendTastyMeal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Clase controladora que conté els endpoints de gestió d'usuaris.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint per obtenir tots els usuaris registrats.
     * @return Una llista amb tots els usuaris
     * @author Albert Borras
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpServletRequest header) {
        System.out.println(header);
        ArrayList<UserModel> listUsers = userService.getUsers();
        return ResponseEntity.ok(listUsers);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<?> getMyUser(HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);

        if(!validationResponse.isValid()) {
            return ResponseEntity.ok("INVALID TOKEN");
        }
        String username = validationResponse.getUsername();
        Optional<UserModel> userOptional = userService.getUserByUsername(username);

        if (!userOptional.isPresent()) {
            ResponseEntity.ok("USER NOT FOUND");
        }

        UserModel user = userOptional.get();

        return ResponseEntity.ok(user);
    }

    /**
     * Endpoint per obtenir l'usuari a través del seu id.
     * @param id Id de l'usuari
     * @return L'usuari
     * @author Albert Borras
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "user/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id) {
        return this.userService.getUserById(id);
    }

    /**
     * Endpoint per eliminar l'usuari.
     * @param id Id de l'usuari a eliminar.
     * @return Missatge notificant si s'ha eliminat o no l'usuari.
     * @author Albert Borras
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "user/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        boolean ok = this.userService.deleteUserById(id);

        if (ok) {
            return "User with id " + id + " deleted";
        } else {
            return "Error deleting user with id " + id;
        }
    }
}