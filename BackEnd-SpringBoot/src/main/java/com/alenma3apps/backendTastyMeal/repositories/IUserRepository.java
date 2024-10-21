package com.alenma3apps.backendTastyMeal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.alenma3apps.backendTastyMeal.models.UserModel;

/**
 * Interfície per accedir a les dades de la taula users de la bd.
 * @author Albert Borras
 */
@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    
}
