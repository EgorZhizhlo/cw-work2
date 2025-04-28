package com.example.autoservice.controller;

import com.example.autoservice.dto.UserDTO;
import com.example.autoservice.model.User;
import com.example.autoservice.security.JwtService;
import com.example.autoservice.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService  jwtService;

    /* ---------- ЛОГИН ---------- */

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @Transactional(readOnly = true)
    @PostMapping("/login")
    public String doLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response,
            Model model) {

        UserDTO authDto;
        try {
            authDto = authService.authenticate(email, password);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "login";
        }

        // в JWT кладём только необходимые поля (email, id и т.п.), без фото
        String token = jwtService.generateToken(authDto.getEmail());
        createJwtCookie(response, token);
        return "redirect:/";
    }

    /* --------- РЕГИСТРАЦИЯ --------- */

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute UserDTO dto,
                             @RequestParam(name = "photoFile", required = false)
                             MultipartFile photo,
                             HttpServletResponse response) throws IOException {

        if (photo != null && !photo.isEmpty()) {
            dto.setPhoto(photo.getBytes());          // кладём БАЙТЫ в DTO
        }

        authService.register(dto);                   // сохранится вместе с фото

        createJwtCookie(response, jwtService.generateToken(dto.getEmail()));
        return "redirect:/";
    }

    /* ---------- ЛОГАУТ ---------- */

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }

    /* ---------- helper ---------- */
    private void createJwtCookie(HttpServletResponse resp, String jwt) {
        Cookie c = new Cookie("JWT_TOKEN", jwt);
        c.setHttpOnly(true);
        c.setPath("/");
        c.setMaxAge(30 * 24 * 60 * 60); // 30 дней
        resp.addCookie(c);
    }
}
