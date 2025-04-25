package com.example.autoservice.controller.admin;

import com.example.autoservice.model.WorkingPlace;
import com.example.autoservice.service.AdminWorkingPlaceService;
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
@RequestMapping("/admin/working-places")
@RequiredArgsConstructor
public class WorkingPlaceAdminController {

    private final AdminWorkingPlaceService wpService;
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
        Sort.Direction d = Sort.Direction.fromString(dir);
        var list = wpService.findAll(Sort.by(d, sort));
        m.addAttribute("wps", list);
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/working-places/list";
    }

    @GetMapping("/new")
    public String formNew(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("wp", new WorkingPlace());
        return "admin/working-places/form";
    }

    @PostMapping("/save")
    public String save(@AuthenticationPrincipal UserDetails ud,
                       @ModelAttribute WorkingPlace wp) {
        if (!isAdmin(ud)) return "error/403";
        wpService.save(wp);
        return "redirect:/admin/working-places";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(@AuthenticationPrincipal UserDetails ud,
                           @PathVariable Long id, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("wp", wpService.findById(id));
        return "admin/working-places/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@AuthenticationPrincipal UserDetails ud,
                         @PathVariable Long id) {
        if (!isAdmin(ud)) return "error/403";
        wpService.deleteById(id);
        return "redirect:/admin/working-places";
    }
}
