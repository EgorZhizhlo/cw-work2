package com.example.autoservice.config;

import com.example.autoservice.dto.UserDTO;
import com.example.autoservice.mapper.UserMapper;
import com.example.autoservice.model.User;
import com.example.autoservice.repository.AdminBaseRepository;
import com.example.autoservice.repository.EmployeeBaseRepository;
import com.example.autoservice.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    private final UserService userService;
    private final UserMapper userMapper;
    private final EmployeeBaseRepository empRepo;
    private final AdminBaseRepository adminRepo;

    public GlobalModelAttributes(UserService userService,
                                 UserMapper userMapper,
                                 EmployeeBaseRepository empRepo,
                                 AdminBaseRepository adminRepo) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.empRepo = empRepo;
        this.adminRepo = adminRepo;
    }

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken);
    }

    @Transactional(readOnly = true)
    @ModelAttribute("currentUser")
    public UserDTO currentUser() {
        if (!isAuthenticated()) return null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userService.findByEmail(email);
        return userMapper.toDto(user);
    }

    @ModelAttribute("isEmployee")
    public boolean isEmployee() {
        if (!isAuthenticated()) return false;
        UserDTO u = currentUser();
        return u != null && empRepo.existsById(u.getId());
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        if (!isAuthenticated()) return false;
        UserDTO u = currentUser();
        return u != null && adminRepo.existsById(u.getId());
    }
}
