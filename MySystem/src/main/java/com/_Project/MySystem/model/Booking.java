package com._Project.MySystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;

    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isCancelled;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Offering offering;

    public Booking(Client client, Offering offering, LocalTime startTime, LocalTime endTime) {
        this.client = client;
        this.offering = offering;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancelled = false;
    }
}
