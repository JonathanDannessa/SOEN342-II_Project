package com._Project.MySystem;

import com._Project.MySystem.model.Instructor;
import com._Project.MySystem.model.Lesson;
import com._Project.MySystem.model.Location;
import com._Project.MySystem.model.Schedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class MySystemApplication {

	public static void main(String[] args) {

		Instructor instructor = new Instructor();
		instructor.setFirstName("John");
		Lesson lesson1 = new Lesson();
		lesson1.setName("Judo");
		lesson1.setIsPrivate(false);
		lesson1.setInstructor(instructor);
		lesson1.setStartTime(LocalTime.of(3,20));
		lesson1.setEndTime(LocalTime.of(4,20));
		Lesson lesson2 = new Lesson();
		lesson2.setInstructor(instructor);
		lesson2.setName("Judo");
		lesson2.setIsPrivate(true);
		lesson2.setStartTime(LocalTime.of(4,20));
		lesson2.setEndTime(LocalTime.of(5,20));
		Lesson lesson3 = new Lesson();
		lesson3.setInstructor(instructor);
		lesson3.setName("Swimming");
		lesson3.setIsPrivate(false);
		lesson3.setStartTime(LocalTime.of(5,20));
		lesson3.setEndTime(LocalTime.of(6,20));
		Schedule schedule = new Schedule();
		schedule.setTypeOfLesson("Judo");
		schedule.setStartDate(LocalDate.now());
		schedule.setEndDate(LocalDate.now().plusDays(2));
		schedule.setStartTime(LocalTime.of(3,20));
		schedule.setEndTime(LocalTime.of(8,20));
		schedule.addLesson(lesson1);
		schedule.addLesson(lesson2);
		schedule.addLesson(lesson3);

		Location location = new Location();
		location.setName("EV-Building gym Room 7");
		location.setCity("Montreal");
		location.getScheduleList().add(schedule);

		System.out.println(location);

		SpringApplication.run(MySystemApplication.class, args);
	}

}
