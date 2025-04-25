package com.example.autoservice.service;

import com.example.autoservice.model.User;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface AdminUserService {
    List<User> findAll();
    List<User> findAll(Sort sort);
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
}
