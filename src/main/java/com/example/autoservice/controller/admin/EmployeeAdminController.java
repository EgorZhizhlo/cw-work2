package com.example.autoservice.controller.admin;

import com.example.autoservice.model.EmployeeBase;
import com.example.autoservice.model.User;
import com.example.autoservice.service.AdminEmployeeService;
import com.example.autoservice.service.AdminService;
import com.example.autoservice.service.AdminUserService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/employees")
@RequiredArgsConstructor
public class EmployeeAdminController {

    private final AdminEmployeeService empService;
    private final AdminUserService adminUserService;
    private final AdminService adminService;
    private final UserService authUserService;

    private boolean isAdmin(UserDetails ud) {
        var u = authUserService.findByEmail(ud.getUsername());
        return adminService.isAdmin(u.getId());
    }

    @GetMapping
    public String list(
            @AuthenticationPrincipal UserDetails ud,
            @RequestParam(defaultValue = "user.id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        var items = empService.findAll(Sort.by(Sort.Direction.fromString(dir), sort));
        m.addAttribute("employees", items);
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/employees/list";
    }

    @GetMapping("/new")
    public String formNew(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("employee", new EmployeeBase());
        m.addAttribute("users", adminUserService.findAll());
        return "admin/employees/form";
    }

    @GetMapping("/edit/{userId}")
    public String formEdit(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long userId,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("employee", empService.findById(userId));
        m.addAttribute("users", adminUserService.findAll());
        return "admin/employees/form";
    }

    @PostMapping("/save")
    public String save(
            @AuthenticationPrincipal UserDetails ud,
            @ModelAttribute EmployeeBase employee,
            @RequestParam Long userId,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        // Set associated user explicitly
        User user = adminUserService.findById(userId);
        employee.setUser(user);
        try {
            empService.save(employee);
            return "redirect:/admin/employees";
        } catch (ObjectOptimisticLockingFailureException ex) {
            m.addAttribute("errorMessage",
                    "Сотрудник уже был изменён другим пользователем. Пожалуйста, обновите страницу и попробуйте снова.");
            m.addAttribute("employee", empService.findById(employee.getUser().getId()));
            m.addAttribute("users", adminUserService.findAll());
            return "admin/employees/form";
        }
    }

    @GetMapping("/delete/{userId}")
    public String delete(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long userId
    ) {
        if (!isAdmin(ud)) return "error/403";
        empService.deleteById(userId);
        return "redirect:/admin/employees";
    }
}
