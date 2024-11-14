package com._Project.MySystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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

    // New flag to indicate if the offering should be shown to the public
    private Boolean availableToPublic = false;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Schedule schedule;

    // Method to check if any lessons have been assigned an instructor
    public void checkIfAvailableToPublic() {
        this.availableToPublic = lessons.stream().anyMatch(Lesson::getInstructorAssigned);
    }
}
