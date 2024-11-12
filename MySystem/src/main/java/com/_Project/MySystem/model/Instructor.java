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
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String specialization;
    @NotNull
    private String email;
    private String password;

    @ElementCollection
    private List<String> availableCities = new ArrayList<>();

    @OneToMany(mappedBy = "instructor", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Offering> offerings = new ArrayList<>();

    public boolean checkCities(String city) {
        return availableCities.contains(city);
    }

    @PreRemove
    public void preRemove() {
        for (Offering offering : offerings) {
            offering.setInstructor(null);
            offering.setInstructorApplied(false);
            offering.setIsAvailable(false);
        }
    }
}

