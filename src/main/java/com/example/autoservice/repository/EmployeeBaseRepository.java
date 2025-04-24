package com.example.autoservice.repository;

import com.example.autoservice.model.EmployeeBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeBaseRepository extends JpaRepository<EmployeeBase, Long> {
    Optional<EmployeeBase> findByUserId(Long userId);
}
