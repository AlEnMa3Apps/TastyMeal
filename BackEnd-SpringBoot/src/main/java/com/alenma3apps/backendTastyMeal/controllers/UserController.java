package com.alenma3apps.backendTastyMeal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.services.UserService;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Clase controladora que conté els endpoints de gestió d'usuaris.
 * @author Albert Borras
 */
@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint per obtenir tots els usuaris registrats.
     * @return Una llista amb tots els usuaris
     */
    @GetMapping
    public ArrayList<UserModel> getUsers() {
        return this.userService.getUsers();
    }

    /**
     * Endpoint per obtenir l'usuari a través del seu id.
     * @param id Id de l'usuari
     * @return L'usuari
     */
    @GetMapping(path = "user/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id) {
        return this.userService.getUserById(id);
    }

    /**
     * Endpoint per eliminar l'usuari.
     * @param id Id de l'usuari a eliminar.
     * @return Missatge notificant si s'ha eliminat o no l'usuari.
     */
    @DeleteMapping(path = "user/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        boolean ok = this.userService.deleteUser(id);

        if (ok) {
            return "User with id " + id + " deleted";
        } else {
            return "Error deleting user with id " + id;
        }
    }
}