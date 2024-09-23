package com.example.course_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title", "Admin Dashboard");
        return "admin"; 
    }
}
