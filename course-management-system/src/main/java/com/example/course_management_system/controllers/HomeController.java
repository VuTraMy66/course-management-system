package com.example.course_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.course_management_system.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired 
    private AuthService authService;
    
    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping({"/home", "/"})
    public String viewHomePage(Model model) {
        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
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

    @RequestMapping("/student/dashboard")
    public String studentDashboard(HttpServletRequest request, Model model) {
        model.addAttribute("pageUrl", request.getRequestURI());
        return "student-dashboard";
    }

    @RequestMapping("/student/review")
    public String studentReview(HttpServletRequest request, Model model) {
        model.addAttribute("pageUrl", request.getRequestURI());
        return "student-review";
    }
    
}
