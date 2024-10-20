package com.example.course_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("pageUrl", "/admin");
        return "admin"; 
    }

    @GetMapping("/admin-all-course")
    public String adminAllCourse(Model model) {
        model.addAttribute("pageUrl", "/admin-all-course");
        return "admin-all-course"; 
    }

    @GetMapping("/admin-course-category")
    public String adminCourseCategory(Model model) {
        model.addAttribute("pageUrl", "/admin-course-category");
        return "admin-course-category"; 
    }

    @GetMapping("/admin-course-category2")
    public String adminCourseCategory2(Model model) {
        model.addAttribute("pageUrl", "/admin-course-category2");
        return "admin-course-category2"; 
    }

    @GetMapping("/admin-course-detail")
    public String adminCourseDetail(Model model) {
        model.addAttribute("pageUrl", "/admin-course-detail");
        return "admin-course-detail"; 
    }

    @GetMapping("/admin-student")
    public String adminStudent(Model model) {
        model.addAttribute("pageUrl", "/admin-student");
        return "admin-student"; 
    }
}
