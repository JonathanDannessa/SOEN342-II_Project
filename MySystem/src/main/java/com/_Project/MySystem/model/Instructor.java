package com._Project.MySystem.model;
import lombok.Data;
import java.util.List;

@Data
public class Instructor {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String specialization;
    private List<String> availableCities;
    private List<Offering> takenOnOfferings;

    public boolean isAvailableForOffering(String type, String city){
        return (specialization.equals(type) && availableCities.contains(city));
    }
    public void takeOffering(Offering offering){
        takenOnOfferings.add(offering);
    }
}
