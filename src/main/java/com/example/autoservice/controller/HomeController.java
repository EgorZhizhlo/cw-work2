package com.example.autoservice.controller;

import com.example.autoservice.model.News;
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

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NewsService     newsService;
    private final EmployeeService empService;
    private final UserService     userService;
    private final CarService      carService;

    @GetMapping("/")
    public String home(Model model) {
        // последние новости
        model.addAttribute("newsList", newsService.getAll());

        // статистика
        model.addAttribute("employeeCount", empService.count());
        model.addAttribute("clientCount",   userService.count());
        model.addAttribute("carCount",      carService.count());

        return "home";
    }

    /**
     * Возвращает изображение новости по id.
     * Если картинка не найдена — возвращаем 404.
     */
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
