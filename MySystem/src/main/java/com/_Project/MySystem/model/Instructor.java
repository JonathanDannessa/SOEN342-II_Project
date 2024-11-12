package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String specialization;
    private String email;
    private String password;
    @ElementCollection
    private List<String> availableCities = new ArrayList<>();

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offering> takenOnOfferings = new ArrayList<>();

    public Instructor(String firstName, String lastName, String phoneNumber, String specialization, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.email = email;
        this.password = password;
    }

    public boolean isAvailableForOffering(String type, String city){
        return (specialization.equals(type) && availableCities.contains(city));
    }

    public void takeOffering(Offering offering){
        takenOnOfferings.add(offering);
    }
}
