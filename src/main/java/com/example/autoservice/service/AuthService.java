package com.example.autoservice.service;

import com.example.autoservice.dto.UserDTO;

public interface AuthService {

    /**
     * Аутентифицирует пользователя по email и паролю.
     * @return DTO пользователя, если пароль верный; иначе null или бросает RuntimeException.
     */
    UserDTO authenticate(String email, String password);

    /**
     * Регистрирует нового пользователя: сохраняет в БД и возвращает DTO созданного пользователя.
     */
    UserDTO register(UserDTO dto);
}
