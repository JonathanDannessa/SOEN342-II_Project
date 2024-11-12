package com._Project.MySystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String typeOfLesson;
    private Boolean isAvailable;
    private Boolean instructorApplied;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void verifyAndSetInstructor(Instructor instructor,String specialization, String city) {
        if(!instructorApplied && instructor.getSpecialization().equals(specialization) && instructor.checkCities(city) ) {
            instructorApplied = true;
            isAvailable = true;
            this.instructor = instructor;
        }
    }
}
