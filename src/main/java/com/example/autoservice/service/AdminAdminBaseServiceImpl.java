package com.example.autoservice.service;

import com.example.autoservice.model.AdminBase;
import com.example.autoservice.repository.AdminBaseRepository;
import com.example.autoservice.service.AdminAdminBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAdminBaseServiceImpl implements AdminAdminBaseService {
    private final AdminBaseRepository repo;

    @Override public List<AdminBase> findAll()                  { return repo.findAll(); }
    @Override public List<AdminBase> findAll(Sort sort)         { return repo.findAll(sort); }
    @Override public AdminBase findById(Long id)               { return repo.findById(id).orElseThrow(); }
    @Override public AdminBase save(AdminBase a)                { return repo.save(a); }
    @Override public void deleteById(Long id)                  { repo.deleteById(id); }
}
