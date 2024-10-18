package com._Project.MySystem.model;

import lombok.Data;

import java.util.List;

@Data
public class Offering {
    private String typeOfLesson;
    private Location location;
    private Instructor instructor;
    private List<Lesson> lessons;
    private Schedule schedule;
    private Boolean isAvailable;

    public Offering(String typeOfLesson, Location location, List<Lesson> lessons, Schedule schedule, Boolean isAvailable) {
        this.typeOfLesson = typeOfLesson;
        this.location = location;
        this.lessons = lessons;
        this.schedule = schedule;
        this.isAvailable = isAvailable;
    }

    public void assignInstructor(Instructor instructor) {
        if(isAvailable && instructor.isAvailableForOffering(typeOfLesson,location.getCity())) {
            this.instructor = instructor;
            this.isAvailable = false;
        }
    }
}
