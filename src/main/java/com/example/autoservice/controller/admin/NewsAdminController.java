package com.example.autoservice.controller.admin;

import com.example.autoservice.model.News;
import com.example.autoservice.service.AdminNewsService;
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
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class NewsAdminController {

    private final AdminNewsService newsService;
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
        var list = newsService.findAll(Sort.by(d, sort));
        m.addAttribute("newsList", list);
        m.addAttribute("currentSort", sort);
        m.addAttribute("currentDir", dir);
        return "admin/news/list";
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<ByteArrayResource> photo(@PathVariable Long id) {
        News n = newsService.findById(id);
        if (n.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(n.getPhoto()));
    }

    @GetMapping("/new")
    public String formNew(@AuthenticationPrincipal UserDetails ud, Model m) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("news", new News());
        return "admin/news/form";
    }

    @PostMapping("/save")
    public String save(
            @AuthenticationPrincipal UserDetails ud,
            @ModelAttribute News news,
            @RequestParam(value="photoFile", required=false) MultipartFile photo
    ) throws IOException {
        if (!isAdmin(ud)) return "error/403";
        if (photo != null && !photo.isEmpty()) {
            news.setPhoto(photo.getBytes());
        }
        news.setDatetime(LocalDateTime.now());
        newsService.save(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id,
            Model m
    ) {
        if (!isAdmin(ud)) return "error/403";
        m.addAttribute("news", newsService.findById(id));
        return "admin/news/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @AuthenticationPrincipal UserDetails ud,
            @PathVariable Long id
    ) {
        if (!isAdmin(ud)) return "error/403";
        newsService.deleteById(id);
        return "redirect:/admin/news";
    }
}
