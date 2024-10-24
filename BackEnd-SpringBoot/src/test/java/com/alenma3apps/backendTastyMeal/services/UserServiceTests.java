package com.alenma3apps.backendTastyMeal.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alenma3apps.backendTastyMeal.dto.request.RegisterRequest;
import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

/**
 * Classe test per testejar les funcions 
 * implementades a la classe UserService.
 * @author Albert Borras
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Test per comprvar que s'obté el llistat de tots els usuaris.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_getUsers() {
        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        ArrayList<UserModel> userList = new ArrayList<UserModel>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        ArrayList<UserModel> userListReturn = userService.getUsers();

        Assertions.assertThat(userListReturn).isNotNull();
    }

    /**
     * Test per comprovar que es registra l'usuari.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_registerUser() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setFirstName("Test");
        request.setLastName("Unit");
        request.setPassword("1234");
        request.setUsername("UserTest");

        UserModel savedUser = new UserModel();
        savedUser.setActive(true);
        savedUser.setEmail("test@test.com");
        savedUser.setFirstName("Test");
        savedUser.setLastName("Unit");
        savedUser.setPassword("1234");
        savedUser.setRole(RoleModel.USER);
        savedUser.setUsername("UserTest");

        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(savedUser);

        UserModel result = userService.registerUser(request);

        Assertions.assertThat(result).isNotNull();
    }

    /**
     * Test per comprovar que s'obté un usuari específic  
     * a través del seu paràmetre id.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_getUserById() {
        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        when(userRepository.findById((long) 1)).thenReturn(Optional.ofNullable(user));

        Optional<UserModel> userReturn = userService.getUserById((long) 1);

        Assertions.assertThat(userReturn).isNotNull();
    }

    /**
     * Test per comprovar que s'elimina l'usuari amb el 
     * paràmetre id especificat.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_deleteUserById() {

        UserModel user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        boolean userDeleted = userService.deleteUserById((long) 1);

        Assertions.assertThat(userDeleted).isTrue();
    }
}
