package com._Project.MySystem.repository;

import com._Project.MySystem.model.Booking;
import com._Project.MySystem.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByLesson(Lesson lessonToRemove);
}
