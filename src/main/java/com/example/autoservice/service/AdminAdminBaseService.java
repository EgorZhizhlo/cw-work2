package com.example.autoservice.service;

import com.example.autoservice.model.AdminBase;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface AdminAdminBaseService {
    List<AdminBase> findAll();
    List<AdminBase> findAll(Sort sort);
    AdminBase findById(Long userId);
    AdminBase save(AdminBase adminBase);
    void deleteById(Long userId);
}
