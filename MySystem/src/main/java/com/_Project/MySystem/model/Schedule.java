package com._Project.MySystem.model;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Schedule {
    private LocalDate startDate;
    private LocalDate endDate;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
