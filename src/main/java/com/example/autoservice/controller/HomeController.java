package com.example.autoservice.controller;

import com.example.autoservice.model.News;
import com.example.autoservice.service.AdminService;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.EmployeeService;
import com.example.autoservice.service.NewsService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NewsService     newsService;
    private final EmployeeService empService;
    private final UserService     userService;
    private final CarService      carService;
    private final AdminService    adminService;

    @GetMapping("/")
    public String home(Model model) {
        // последние новости
        model.addAttribute("newsList", newsService.getAll());

        // статистика
        long employeeCount = empService.count();
        long adminCount    = adminService.countAdmins();
        long totalUsers    = userService.count();
        long clientCount   = totalUsers - adminCount - employeeCount;
        long carCount      = carService.count();

        model.addAttribute("employeeCount", employeeCount);
        model.addAttribute("adminCount",    adminCount);
        model.addAttribute("clientCount",   clientCount);
        model.addAttribute("carCount",      carCount);

        return "home";
    }

    /**
     * Возвращает изображение новости по id.
     * Если картинка не найдена — возвращаем 404.
     */
    @Transactional(readOnly = true)
    @GetMapping(value = "/news/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> newsImage(@PathVariable Long id) {
        return newsService.findById(id)
                .map(News::getPhoto)                  // ← используем getPhoto()
                .filter(data -> data != null && data.length > 0)
                .map(data -> ResponseEntity.ok().body(data))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
