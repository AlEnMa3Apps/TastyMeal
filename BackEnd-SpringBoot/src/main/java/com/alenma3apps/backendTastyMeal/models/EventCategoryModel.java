package com.alenma3apps.backendTastyMeal.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventCategories", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class EventCategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String category;

    @OneToMany(mappedBy = "eventCategory")
    @JsonIgnore
    private List<EventModel> events = new ArrayList<>();

}
