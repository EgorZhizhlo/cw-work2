package com.example.autoservice.service;

import com.example.autoservice.model.EmployeeBase;
import com.example.autoservice.repository.EmployeeBaseRepository;
import com.example.autoservice.service.AdminEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEmployeeServiceImpl implements AdminEmployeeService {
    private final EmployeeBaseRepository repo;

    @Override public List<EmployeeBase> findAll()              { return repo.findAll(); }
    @Override public List<EmployeeBase> findAll(Sort sort)     { return repo.findAll(sort); }
    @Override public EmployeeBase findById(Long id)            { return repo.findById(id).orElseThrow(); }
    @Override public EmployeeBase save(EmployeeBase e)         { return repo.save(e); }
    @Override public void deleteById(Long id)                  { repo.deleteById(id); }
}
