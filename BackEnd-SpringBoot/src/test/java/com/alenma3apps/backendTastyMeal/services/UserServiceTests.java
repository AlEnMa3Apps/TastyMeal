package com.alenma3apps.backendTastyMeal.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alenma3apps.backendTastyMeal.dto.request.EditUserRequest;
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

    private UserModel user;
    private RegisterRequest request;
    UserModel savedUser;
    EditUserRequest editUserRequest;

    @BeforeEach
    public void init() {
        user = new UserModel();
        user.setActive(true);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Unit");
        user.setPassword("1234");
        user.setRole(RoleModel.USER);
        user.setUsername("UserTest");

        request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setFirstName("Test");
        request.setLastName("Unit");
        request.setPassword("1234");
        request.setUsername("UserTest");

        savedUser = new UserModel();
        savedUser.setActive(true);
        savedUser.setEmail("test@test.com");
        savedUser.setFirstName("Test");
        savedUser.setLastName("Unit");
        savedUser.setPassword("1234");
        savedUser.setRole(RoleModel.USER);
        savedUser.setUsername("UserTest");

        editUserRequest = new EditUserRequest();
        editUserRequest.setActive(true);
        editUserRequest.setEmail("test@test.com");
        editUserRequest.setFirstName("Test");
        editUserRequest.setLastName("Unit");
        editUserRequest.setPassword("1234");
        editUserRequest.setRole("USER");
        editUserRequest.setUsername("UserTest");
    }

    /**
     * Test per comprvar que s'obté el llistat de tots els usuaris.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_getUsers() {
        
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

        when(userRepository.findById((long) 1)).thenReturn(Optional.ofNullable(user));

        Optional<UserModel> userReturn = userService.getUserById((long) 1);

        Assertions.assertThat(userReturn).isNotNull();
    }

    /**
     * Test per comprovar que s'obté un usuari específic  
     * a través del seu nom d'usuari.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_getUserByUsername() {

        when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        Optional<UserModel> userReturn = userService.getUserByUsername("UserTest");

        Assertions.assertThat(userReturn).isNotNull();
    }

    /**
     * Test per comprovar que es canvia la contrasenya de l'usuari.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_changePassword() {

        when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));
        
        ResponseEntity<?> changedPassword = userService.changePassword("UserTest", "112233");

        Assertions.assertThat(changedPassword.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    /**
     * Test per comprovar que s'edita un usuari específic  
     * a través del seu paràmetre id.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_editUserById() {

        when(userRepository.findById((long) 1)).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> userEdited = userService.editUserById((long) 1, editUserRequest);

        Assertions.assertThat(userEdited.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'edita un usuari específic  
     * a través del seu nom d'usuari.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_editUser() {

        when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> userEdited = userService.editUser("UserTest", editUserRequest);

        Assertions.assertThat(userEdited.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'elimina l'usuari amb el 
     * paràmetre id especificat.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_deleteUserById() {

        ResponseEntity<?> userDeleted = userService.deleteUserById((long) 1);

        Assertions.assertThat(userDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * Test per comprovar que s'elimina l'usuari amb el 
     * seu nom d'usuari.
     * @author Albert Borras
     */
    @Test
    public void UserServiceTest_deleteUserByUsername() {

        when(userRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.ofNullable(user));

        ResponseEntity<?> userDeleted = userService.deleteUserByUsername("UserTest");

        Assertions.assertThat(userDeleted.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
