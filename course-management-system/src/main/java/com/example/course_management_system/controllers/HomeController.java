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
    public String teacherDashboard() {
        return "teacher";
    }

    @GetMapping("teacher-course")
    public String teacherCourse() {
        return "teacher-course";
    }

    @GetMapping("teacher-student")
    public String teacherStudent() {
        return "teacher-student";
    }

    @GetMapping("/student")
    public String studentDashboard() {
        return "student";
    }

    @GetMapping("/course")
    public String courseList() {
        return "course";
    }
}
