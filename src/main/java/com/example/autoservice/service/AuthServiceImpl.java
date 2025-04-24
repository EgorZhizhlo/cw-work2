package com.example.autoservice.service;

import com.example.autoservice.dto.UserDTO;
import com.example.autoservice.model.User;
import com.example.autoservice.mapper.UserMapper;
import com.example.autoservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDTO authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO register(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email уже зарегистрирован");
        }
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }
}
