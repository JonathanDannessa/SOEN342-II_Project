package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private LocalDate startDate;
    private LocalDate endDate;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    private Offering offering;
}
