package com.example.autoservice.service;

import com.example.autoservice.repository.AdminBaseRepository;
import com.example.autoservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminBaseRepository adminBaseRepo;

    @Override
    public boolean isAdmin(Long userId) {
        return adminBaseRepo.existsById(userId);
    }

    @Override
    public long countAdmins() {
        return adminBaseRepo.count();
    }
}
