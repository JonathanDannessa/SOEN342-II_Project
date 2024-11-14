package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private Boolean isPrivateLesson;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "offering_id")
    private Offering offering;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    // Flag to indicate if an instructor has been assigned to this lesson
    private Boolean instructorAssigned = false;

    private Boolean isBooked = false;

    // Assign the instructor to the lesson and set instructorAssigned to true
    public void assignInstructor(Instructor instructor, String city) {
        if (!instructorAssigned && instructor.getSpecialization().equals(this.name) && instructor.checkCities(city)) {
            this.instructor = instructor;
            this.instructorAssigned = true;
            this.offering.checkIfAvailableToPublic();
        }
    }
}

