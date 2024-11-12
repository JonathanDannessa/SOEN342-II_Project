package com._Project.MySystem.repository;

import com._Project.MySystem.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Instructor findByEmail(String email);
}
