package com.example.autoservice.controller.admin;

import com.example.autoservice.model.Schedule;
import com.example.autoservice.service.AdminScheduleService;
import com.example.autoservice.service.AdminEmployeeService;
import com.example.autoservice.service.AdminCarService;
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
@RequestMapping("/admin/schedules")
@RequiredArgsConstructor
public class ScheduleAdminController {

    private final AdminScheduleService schedService;
    private final AdminEmployeeService adminEmployeeService;  // список сотрудников
    private final AdminCarService adminCarService;            // список машин
    private final AdminService adminService;
    private final UserService authUserService;

    private boolean isAdmin(UserDetails ud) {
        var u = authUserService.findByEmail(ud.getUsername());
        return adminService.isAdmin(u.getId());
    }

    @GetMapping
    public String list(
            @AuthenticationPrincipal UserDetails ud,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        var items = schedService.findAll(Sort.by(Sort.Direction.fromString(dir), sort));
        m.addAttribute("schedules", items);
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/schedules/list";
    }

    @GetMapping("/new")
    public String formNew(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("schedule", new Schedule());
        m.addAttribute("employees", adminEmployeeService.findAll());
        m.addAttribute("cars", adminCarService.findAll());
        return "admin/schedules/form";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("schedule", schedService.findById(id));
        m.addAttribute("employees", adminEmployeeService.findAll());
        m.addAttribute("cars", adminCarService.findAll());
        return "admin/schedules/form";
    }

    @PostMapping("/save")
    public String save(
            @AuthenticationPrincipal UserDetails ud,
            @ModelAttribute Schedule schedule
    ) {
        if (!isAdmin(ud)) return "error/403";
        schedService.save(schedule);
        return "redirect:/admin/schedules";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id
    ) {
        if (!isAdmin(ud)) return "error/403";
        schedService.deleteById(id);
        return "redirect:/admin/schedules";
    }
}
