package com._Project.MySystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offering_Id;

    private String typeOfLesson;
    private Boolean isAvailable;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Instructor instructor;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToOne
    private Schedule schedule;

    public Offering(String typeOfLesson, Location location, List<Lesson> lessons, Schedule schedule, Boolean isAvailable) {
        this.typeOfLesson = typeOfLesson;
        this.location = location;
        this.lessons = lessons;
        this.schedule = schedule;
        this.isAvailable = isAvailable;
    }

    public void assignInstructor(Instructor instructor) {
        if(isAvailable && instructor.isAvailableForOffering(typeOfLesson, location.getCity())) {
            this.instructor = instructor;
            this.isAvailable = false;
        }
    }
}
