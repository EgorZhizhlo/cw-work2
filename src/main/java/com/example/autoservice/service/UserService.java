package com.example.autoservice.service;

import com.example.autoservice.model.User;

import java.util.Optional;
import java.util.List;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    long count();

}
