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

    private final UserRepository    userRepository;
    private final PasswordEncoder   passwordEncoder;
    private final UserMapper        userMapper;

    @Override
    public UserDTO authenticate(String email, String password) {
        // 1) Лёгкая проекция — без фото и без LOB
        UserRepository.AuthUserProjection light = userRepository
                .findLightByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден (проекция)"));

        // 2) Оригинальный User для проверки пароля
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден (для пароля)"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        // 3) Собираем DTO для контроллера/токена
        return UserDTO.builder()
                .id(light.getId())
                .surname(light.getSurname())
                .name(light.getName())
                .patronymic(light.getPatronymic())
                .phoneNumber(light.getPhoneNumber())
                .email(light.getEmail())
                .build();
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
