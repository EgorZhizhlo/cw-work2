package com.example.autoservice.controller;

import com.example.autoservice.model.User;
import com.example.autoservice.service.AdminService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/admin")
    public String adminDashboard(@AuthenticationPrincipal UserDetails ud) {
        User user = userService.findByEmail(ud.getUsername());
        if (!adminService.isAdmin(user.getId())) {
            return "error/403";
        }
        return "admin";
    }
}
