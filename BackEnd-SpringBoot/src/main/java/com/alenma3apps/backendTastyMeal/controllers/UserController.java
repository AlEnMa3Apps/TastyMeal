package com.alenma3apps.backendTastyMeal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alenma3apps.backendTastyMeal.dto.request.EditUserRequest;
import com.alenma3apps.backendTastyMeal.dto.request.PasswordRequest;
import com.alenma3apps.backendTastyMeal.dto.response.ValidationResponse;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.security.JwtService;
import com.alenma3apps.backendTastyMeal.services.UserService;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

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

    /**
     * Endpoint per obtenir l'usuari que fa la petició.
     * @param header Capçalera de la petició HTTP.
     * @return Les dades de l'usuari que fa la petició.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<?> getMyUser(HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);

        if(!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }
        String username = validationResponse.getUsername();
        Optional<UserModel> userOptional = userService.getUserByUsername(username);

        if (!userOptional.isPresent()) {
            return SpringResponse.userNotFound();
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
    @GetMapping("/user/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    /**
     * Endpoint per canviar la contrasenya.
     * @param header Capçalera de la petició HTTP.
     * @param request Nova contrasenya.
     * @return Missatge confirmant si s'ha canviat o no la contrasenya.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/user")
    public ResponseEntity<?> changePassword(HttpServletRequest header, @RequestBody PasswordRequest request) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);

        if(!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }
        String username = validationResponse.getUsername();

        return userService.changePassword(username, request.getPassword());
    }

    /**
     * Endpoint per editar un usuari pel seu id passat per paràmetre.
     * @param id Id de l'usuari.
     * @param request Noves dades de l'usuari.
     * @return Missatge confirmant si s'ha editat o no l'usuari.
     * @author Albert Borras
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{id}")
    public ResponseEntity<?> editUserById(@PathVariable Long id, @RequestBody EditUserRequest request) {
        return userService.editUserById(id, request);
    }

    /**
     * Endpoint per editar l'usuari que fa la petició.
     * @param header Capçalera de la petició HTTP.
     * @param request Noves dades de l'usuari.
     * @return Missatge confirmant si s'ha editat o no l'usuari.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/user")
    public ResponseEntity<?> editMyUser(HttpServletRequest header, @RequestBody EditUserRequest request) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);

        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String username = validationResponse.getUsername();
        return userService.editUser(username, request);
    }

    /**
     * Endpoint per eliminar l'usuari.
     * @param id Id de l'usuari a eliminar.
     * @return Missatge notificant si s'ha eliminat o no l'usuari.
     * @author Albert Borras
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }

    /**
     * Endpoint per eliminar l'usuari que fa la petició.
     * @param header Capçalera de la petició HTTP.
     * @return Missatge confirmant si s'ha eliminat o no l'usuari.
     * @author Albert Borras
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(path = "/user")
    public ResponseEntity<?> deleteMyUser(HttpServletRequest header) {
        ValidationResponse validationResponse = jwtService.validateTokenAndUser(header);

        if (!validationResponse.isValid()) {
            return SpringResponse.invalidToken();
        }

        String username = validationResponse.getUsername();
        return userService.deleteUserByUsername(username);
    }
}