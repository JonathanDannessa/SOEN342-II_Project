package com._Project.MySystem.repository;

import com._Project.MySystem.model.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
}
