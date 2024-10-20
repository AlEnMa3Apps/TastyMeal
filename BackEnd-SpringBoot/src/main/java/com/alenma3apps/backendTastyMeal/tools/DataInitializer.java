package com.alenma3apps.backendTastyMeal.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.alenma3apps.backendTastyMeal.models.RoleModel;
import com.alenma3apps.backendTastyMeal.models.UserModel;
import com.alenma3apps.backendTastyMeal.repositories.IUserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class DataInitializer {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;

    @PostConstruct
    public void initializer() {
        if (userRepository.count() == 0){
            UserModel admin = new UserModel();
            admin.setUsername("EnricN");
            admin.setPassword(passwordEncoder.encode("root"));
            admin.setEmail("enric_admin@gmail.com");
            admin.setRole(RoleModel.ADMIN);

            userRepository.save(admin);
        }
    }
}
