package com.example.autoservice.service.impl;

import com.example.autoservice.model.User;
import com.example.autoservice.repository.UserRepository;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override public long count() {
        return userRepo.count();
    }
}
