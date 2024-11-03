package com._Project.MySystem.model;

import java.util.List;

public class Admin {

    public void createOffering(String typeOfLesson, Location location, List<Lesson> lessons, Schedule schedule){
        Offering offering = new Offering(typeOfLesson, location, lessons, schedule, true);
        // save offering to database
    }
}
