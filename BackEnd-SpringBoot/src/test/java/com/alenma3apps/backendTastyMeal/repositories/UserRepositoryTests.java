package com.alenma3apps.backendTastyMeal.repositories;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;

/**
 * Classe test per testejar les funcions utilitzades de 
 * la interfície IUserRepository per interactuar amb 
 * la base de dades.
 * @author Albert Borras
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Test per comprovar que l'usuari es guarda a a la base de dades.
     */
    @Test
    public void UserRepositoryTest_saveUser() {
        
        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        UserModel savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    /**
     * Test per comprovar que s'obté l'usuari demanat per 
     * id a la base de dades.
     */
    @Test
    public void UserRepositoryTest_findUserById() {
        
        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        userRepository.save(user);

        UserModel userReturn = userRepository.findById(user.getId()).get();

        Assertions.assertThat(userReturn).isNotNull();
    }

    /**
     * Test per comprovar que s'obté l'usuari demanat per 
     * nom d'usuari a la base de dades.
     */
    @Test
    public void UserRepositoryTest_findUserByUsername() {

        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        userRepository.save(user);

        UserModel userReturn = userRepository.findByUsername(user.getUsername()).get();

        Assertions.assertThat(userReturn).isNotNull();
    }

    /**
     * Test per comprovar que s'ha eliminat l'usuari 
     * especificat per id de la base de dades.
     */
    @Test
    public void UserRepositoryTest_deleteUserById() {

        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<UserModel> userReturn = userRepository.findById(user.getId());

        Assertions.assertThat(userReturn).isEmpty();
    }
}
