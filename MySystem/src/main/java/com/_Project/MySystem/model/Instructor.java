package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String specialization;

    @ElementCollection
    private List<String> availableCities;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offering> takenOnOfferings;

    public boolean isAvailableForOffering(String type, String city){
        return (specialization.equals(type) && availableCities.contains(city));
    }

    public void takeOffering(Offering offering){
        takenOnOfferings.add(offering);
    }
}
