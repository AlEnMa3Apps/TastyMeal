package com.alenma3apps.backendTastyMeal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="recipes", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
public class RecipeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1024, nullable = true)
    private String imageUrl;

    @Column(length = 4096, nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer cookingTime;

    @Column(nullable = false)
    private Integer numPersons;

    @Column(length = 1024, nullable = false)
    private String ingredients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIgnore
    private UserModel ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private CategoryModel recipeCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    @JsonIdentityReference(alwaysAsId = true)
    private List<CommentModel> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    @JsonIdentityReference(alwaysAsId = true)
    private List<ReportModel> reports;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "recipe_favorite", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "username"))
    private List<UserModel> username;

    @JsonProperty("categoryName")
    public String getCategoryName() {
        return recipeCategory != null ? recipeCategory.getCategory() : null;
    }
}