package com._Project.MySystem.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private Boolean isPrivateLesson;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "offering_id")
    private Offering offering;
}
