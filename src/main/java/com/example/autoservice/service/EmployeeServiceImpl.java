package com.example.autoservice.service.impl;

import com.example.autoservice.model.EmployeeBase;
import com.example.autoservice.repository.EmployeeBaseRepository;
import com.example.autoservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeBaseRepository repo;

    @Override
    public EmployeeBase getByUserId(Long userId) {
        // предполагаем, что репозиторий имеет метод findByUserId(...)
        return repo.findByUserId(userId).orElse(null);
    }

    @Override
    public List<EmployeeBase> getAll() {
        return repo.findAll();
    }

    @Override public long count() {
        return repo.count();
    }
}
