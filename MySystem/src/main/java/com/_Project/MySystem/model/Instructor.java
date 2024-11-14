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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> availableCities = new ArrayList<>();

    // Relationships for the lessons the instructor has chosen
    @OneToMany(mappedBy = "instructor", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Lesson> lessons = new ArrayList<>();

    public boolean checkCities(String city) {
        return availableCities.contains(city);
    }

}

