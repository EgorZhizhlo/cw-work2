package com.example.autoservice.controller.admin;

import com.example.autoservice.model.AdminBase;
import com.example.autoservice.model.User;
import com.example.autoservice.service.AdminAdminBaseService;
import com.example.autoservice.service.AdminUserService;
import com.example.autoservice.service.AdminService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/admin-bases")
@RequiredArgsConstructor
public class AdminBaseAdminController {

    private final AdminAdminBaseService adminBaseService;
    private final AdminUserService adminUserService;      // список всех пользователей
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
        var items = adminBaseService.findAll(Sort.by(Sort.Direction.fromString(dir), sort));
        m.addAttribute("admins", items);
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/admin-bases/list";
    }

    @GetMapping("/new")
    public String formNew(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("adminBase", new AdminBase());
        m.addAttribute("users", adminUserService.findAll());  // для выбора userId
        return "admin/admin-bases/form";
    }

    @GetMapping("/edit/{userId}")
    public String formEdit(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long userId,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("adminBase", adminBaseService.findById(userId));
        m.addAttribute("users", adminUserService.findAll());
        return "admin/admin-bases/form";
    }

    @PostMapping("/save")
    public String save(
            @AuthenticationPrincipal UserDetails ud,
            @ModelAttribute AdminBase adminBase
    ) {
        if (!isAdmin(ud)) return "error/403";
        adminBaseService.save(adminBase);
        return "redirect:/admin/admin-bases";
    }

    @GetMapping("/delete/{userId}")
    public String delete(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long userId
    ) {
        if (!isAdmin(ud)) return "error/403";
        adminBaseService.deleteById(userId);
        return "redirect:/admin/admin-bases";
    }
}
