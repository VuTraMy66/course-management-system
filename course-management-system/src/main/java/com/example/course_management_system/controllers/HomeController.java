package com.example.course_management_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }

    // @RequestMapping("/logout")
    // public String logout() {
    //     return "redirect:/login?logout"; // Redirect back to login page after logout
    // }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/student")
    public String student() {
        return "student";
    }

    @RequestMapping("/student-profile")
    public String studentProfile(HttpServletRequest request, Model model) {
        model.addAttribute("pageUrl", request.getRequestURI());
        return "/student-profile";
    }

    @RequestMapping("/student-dashboard")
    public String studentDashboard(HttpServletRequest request, Model model) {
        model.addAttribute("pageUrl", request.getRequestURI());
        return "/student-dashboard";
    }

    @RequestMapping("/student-review")
    public String studentReview(HttpServletRequest request, Model model) {
        model.addAttribute("pageUrl", request.getRequestURI());
        return "/student-review";
    }

    @GetMapping("/course")
    public String courseList() {
        return "course";
    }
    
    @GetMapping("/course-detail")
    public String courseDetail() {
        return "course-detail";
    }
}
