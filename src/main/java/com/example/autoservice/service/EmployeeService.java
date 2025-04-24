package com.example.autoservice.service;

import com.example.autoservice.model.EmployeeBase;

import java.util.List;

public interface EmployeeService {
    EmployeeBase getByUserId(Long userId);
    List<EmployeeBase> getAll();
    long count();  // ← новый метод
}