package com.example.autoservice.service;

import com.example.autoservice.model.EmployeeBase;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface AdminEmployeeService {
    List<EmployeeBase> findAll();
    List<EmployeeBase> findAll(Sort sort);
    EmployeeBase findById(Long id);
    EmployeeBase save(EmployeeBase emp);
    void deleteById(Long id);
}
