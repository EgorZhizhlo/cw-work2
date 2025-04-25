package com.example.autoservice.controller.admin;

import com.example.autoservice.model.Car;
import com.example.autoservice.model.User;
import org.springframework.data.domain.Sort;
import com.example.autoservice.service.AdminCarService;
import com.example.autoservice.service.AdminUserService;
import com.example.autoservice.service.AdminService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cars")
@RequiredArgsConstructor
public class CarAdminController {

    private final AdminCarService carService;
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
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/401";

        Sort.Direction direction = Sort.Direction.fromString(dir);
        var cars = carService.findAll(Sort.by(direction, sort));

        m.addAttribute("cars", cars);
        m.addAttribute("users", adminUserService.findAll());
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/cars/list";
    }
    @GetMapping("/new")
    public String createForm(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("car", new Car());
        m.addAttribute("users", adminUserService.findAll());
        return "admin/cars/form";
    }

    @PostMapping("/save")
    public String save(
            @AuthenticationPrincipal UserDetails ud,
            @ModelAttribute Car car,
            @RequestParam("ownerId") Long ownerId
    ) {
        if (!isAdmin(ud)) return "error/403";
        // связать владельца
        car.setOwner(adminUserService.findById(ownerId));
        carService.save(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("car", carService.findById(id));
        m.addAttribute("users", adminUserService.findAll());
        return "admin/cars/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id
    ) {
        if (!isAdmin(ud)) return "error/403";
        carService.deleteById(id);
        return "redirect:/admin/cars";
    }
}
