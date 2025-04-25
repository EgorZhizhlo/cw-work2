package com.example.autoservice.controller.admin;

import com.example.autoservice.model.User;
import com.example.autoservice.service.AdminUserService;
import com.example.autoservice.service.AdminService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final AdminUserService userService;
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
        var users = userService.findAll(Sort.by(d, sort));
        m.addAttribute("users", users);
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/users/list";
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<ByteArrayResource> photo(@PathVariable Long id) {
        User u = userService.findById(id);
        if (u.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(u.getPhoto()));
    }

    @GetMapping("/new")
    public String formNew(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("user", new User());
        return "admin/users/form";
    }

    @PostMapping("/save")
    public String save(
            @AuthenticationPrincipal UserDetails ud,
            @ModelAttribute User user,
            @RequestParam(value="photoFile", required=false) MultipartFile photo
    ) throws IOException {
        if (!isAdmin(ud)) return "error/403";
        if (photo != null && !photo.isEmpty()) {
            user.setPhoto(photo.getBytes());
        }
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("user", userService.findById(id));
        return "admin/users/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id
    ) {
        if (!isAdmin(ud)) return "error/403";
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
