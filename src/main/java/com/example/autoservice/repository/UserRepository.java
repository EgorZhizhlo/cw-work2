package com.example.autoservice.repository;

import com.example.autoservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // ---- НОВОЕ: интерфейс-проекция ----
    interface AuthUserProjection {
        Long   getId();
        String getSurname();
        String getName();
        String getPatronymic();
        String getPhoneNumber();
        String getEmail();
    }

    // Spring Data сам создаст запрос SELECT только по нужным полям
    Optional<AuthUserProjection> findLightByEmail(String email);
}
