package com.alenma3apps.backendTastyMeal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.EditUserRequest;
import com.alenma3apps.backendTastyMeal.dto.request.RegisterRequest;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;
import com.alenma3apps.backendTastyMeal.tools.SpringResponse;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Classe que gestiona la lògica dels usuaris.
 * @author Albert Borras
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Retorna tots els usuaris de la base de dades.
     * @return Llista amb els usuaris de la base de dades.
     * @author Albert Borras
     */
    public ArrayList<UserModel> getUsers() {
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    /**
     * Registra l'usuari del paràmetre d'entrada a la base de dades.
     * @param request Usuari a registrar
     * @return Usuari registrat a la base de dades.
     * @author Albert Borras
     */
    public UserModel registerUser(RegisterRequest request) {
        UserModel user = new UserModel();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(RoleModel.USER);
        user.setActive(true);

        return userRepository.save(user);
    }

    /**
     * Retorna l'usuari de la base de dades que conté el id passat per paràmetre.
     * @param id id de l'usuari a buscar a la la base de dades.
     * @return L'usuari de la base de dades que té l'id corresponent.
     * @author Albert Borras
     */
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retorna l'usuari de la base de dades amb el nom d'usuari passat per paràmetre.
     * @param username No d'usuari.
     * @return Usuari de la base de dades.
     * @author Albert Borras
     */
    public Optional<UserModel> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Canvia la contrasenya de l'usuari passat per paràmetre per una nova 
     * contrasenya passada per paràmetre.
     * @param username Nom d'usuari.
     * @param password Nova contrasenya.
     * @return Missatge confirmant si s'ha canviat o no la contrasenya.
     * @author Albert Borras
     */
    public ResponseEntity<?> changePassword(String username, String password) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);

        if (!userOptional.isPresent()) {
            return SpringResponse.userNotFound();
        }

        UserModel user = userOptional.get();
        user.setPassword(passwordEncoder.encode(password));
        
        try {
            userRepository.save(user);
            return SpringResponse.passwordChanged();
        } catch (Exception ex) {
            return SpringResponse.errorChangingpassword();
        }
    }

    /**
     * Edita les dades de l'usuari de la base de dades amb l'id passat per paràmetre.
     * @param id Id de l'usuari.
     * @param request Dades actualitzades.
     * @return Missatge confirmant si s'ha editat o no l'usuari.
     * @author Albert Borras
     */
    public ResponseEntity<?> editUserById(Long id, EditUserRequest request) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return SpringResponse.userNotFound();
        }

        try {
            UserModel user = userOptional.get();
            user.setActive(request.isActive());
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(request.getPassword());
            user.setRole(RoleModel.valueOf(request.getRole()));
            user.setUsername(request.getUsername());

            userRepository.save(user);
            return SpringResponse.userEdited();
        } catch (Exception ex) {
            return SpringResponse.errorEditingUser();
        }
    }

    /**
     * Edita les dades de l'usuari de la base de dades amb el nom d'usuari 
     * passat per paràmetre.
     * @param username Nom d'usuari.
     * @param request Dades actualitzades.
     * @return Missatge confirmant si s'ha editat o no l'usuari.
     * @author Albert Borras
     */
    public ResponseEntity<?> editUser(String username, EditUserRequest request) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return SpringResponse.userNotFound();
        }

        try {
            UserModel user = userOptional.get();
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getUsername());

            userRepository.save(user);
            return SpringResponse.userEdited();
        } catch (Exception ex) {
            return SpringResponse.errorEditingUser();
        }
    }
    
    /**
     * Elimina l'usuari de la base de dsdes que conté el id passat per paràmetre.
     * @param id id de l'usuari a eliminar a la base de dades.
     * @return Missatge confirmant si s'ha eliminat o no l'usuari la base de dades.
     * @author Albert Borras
     */
    public ResponseEntity<?> deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return SpringResponse.userDeleted();
        } catch (Exception ex) {
            return SpringResponse.errorDeletingUser();
        }
    }

    /**
     * Elimina l'usuari de la base de dades que conté el nom d'usuari passat per paràmetre.
     * @param username Nom d'usuari.
     * @return Missatge confirmant si s'ha eliminat o no l'usuari la base de dades.
     * @author Albert Borras
     */
    public ResponseEntity<?> deleteUserByUsername(String username) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return SpringResponse.userNotFound();
        }
        UserModel user = userOptional.get();
        Long id = user.getId();
        try {
            userRepository.deleteById(id);
            return SpringResponse.userDeleted();
        } catch (Exception ex) {
            return SpringResponse.errorDeletingUser();
        }
    }

}