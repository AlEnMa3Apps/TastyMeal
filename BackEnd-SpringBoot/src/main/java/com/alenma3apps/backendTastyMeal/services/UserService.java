package com.alenma3apps.backendTastyMeal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alenma3apps.backendTastyMeal.dto.request.RegisterRequest;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

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

    public Optional<UserModel> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Elimina l'usuari de la base de dsdes que conté el id passat per paràmetre.
     * @param id id de l'usuari a eliminar a la base de dades.
     * @return true si l'usuari s'ha eliminat de la base de dades, del contrari retorna false.
     * @author Albert Borras
     */
    public boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}