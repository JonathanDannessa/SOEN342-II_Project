package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Integer age;
    private String password;

    @ManyToOne
    private Client guardian; // Legal guardian for underage clients

    @OneToMany(mappedBy = "client")
    private List<Booking> bookings;

    public Client(String firstName, String lastName, String phoneNumber, String email, Integer age,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public Client(String firstName, String lastName, String phoneNumber, String email, Integer age, String password ,Client guardian) {
        this(firstName,lastName,phoneNumber,email,age,password);
        this.guardian = guardian;
    }

    public boolean isUnderage() {
        return age < 18;
    }

    public boolean hasGuardian() {
        return guardian != null;
    }
}