package com.example.autoservice.repository;

import com.example.autoservice.model.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingPlaceRepository extends JpaRepository<WorkingPlace, Long> {
}
