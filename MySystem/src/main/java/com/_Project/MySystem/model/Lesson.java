package com._Project.MySystem.model;
import lombok.Data;
import java.time.LocalTime;

@Data
public class Lesson {
    private String name;
    private Boolean isPrivate;
    private LocalTime startTime;
    private LocalTime endTime;
}
