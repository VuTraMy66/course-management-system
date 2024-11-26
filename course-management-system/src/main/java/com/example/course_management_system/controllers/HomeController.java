package com.example.course_management_system.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "index";  
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("mess", "Login fail!");
        }
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model) {
        
        Logger logger = LoggerFactory.getLogger(HomeController.class);
        logger.info("Handling login for user: {}", username);

        Users user = userService.handleUserLogin(username, password);
        if (user != null) {
            logger.info("User authenticated: {}", username);
            if ("admin".equals(user.getRole())) {
                logger.info("Redirecting to admin page");
                return "redirect:/admin";
            } else if ("student".equals(user.getRole())) {
                logger.info("Redirecting to student page");
                return "redirect:/";
            } else {
                logger.warn("Unknown role for user: {}", username);
                model.addAttribute("errorMessage", "Unknown role!");
            }
        } else {
            logger.warn("Authentication failed for user: {}", username);
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        return "login";
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
