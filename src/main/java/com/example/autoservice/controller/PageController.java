package com.example.autoservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/about-author")
    public String aboutAuthor() {
        return "about-author";
    }
}
