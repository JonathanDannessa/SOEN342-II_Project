package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Data
public class Lesson {
    private String name;
    private Boolean isPrivate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "offering_Id")  // This column should match the foreign key in the database
    private Offering offering;
}
