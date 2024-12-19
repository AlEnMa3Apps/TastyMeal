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
@Table(name="events", uniqueConstraints = @UniqueConstraint(columnNames = ("title")))
public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 10, nullable = false)
    private String date;

    @Column(nullable = false)
    private Integer duration;

    @Column(length = 4096, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private EventCategoryModel eventCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "event_attendees", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "attendee"))
    private List<UserModel> attendee;

    @JsonProperty("categoryName")
    public String getCategoryName() {
        return eventCategory != null ? eventCategory.getCategory() : null;
    }

}