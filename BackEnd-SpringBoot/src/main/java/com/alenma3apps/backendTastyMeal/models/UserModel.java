package com.alenma3apps.backendTastyMeal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String email;

    @Column(length = 45, nullable = true)
    private String firstName;

    @Column(length = 45, nullable = true)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private RoleModel role;

    @Column
    private Boolean active;
}