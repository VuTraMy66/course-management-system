package com.example.course_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index";  
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/teacher")
    public String teacherPage() {
        return "teacher";
    }

    @GetMapping("/learner")
    public String learnerPage() {
        return "learner";
    }

    @GetMapping("/course")
    public String courseList() {
        return "course";
    }
    
}
