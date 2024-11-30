package com.example.course_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AuthService;
import com.example.course_management_system.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private AuthService authService;
    private UserService userService;

    public UserController(AuthService authService, UserService userService) {
        this.userService = userService;
        this.authService = authService;
    }

    // @GetMapping("/login")
    // public String viewLoginPage() {
    //     return "login";
    // }

    @GetMapping("/student")
    public String student(Model model) {
        Users student = AuthenticationUtils.getAuthenticatedUser(); 
        if (student != null) {
            model.addAttribute("student", student);
        }

        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "student";
    }

    @RequestMapping("/student/profile")
    public String studentProfile(HttpServletRequest request, Model model) {
        Users student = AuthenticationUtils.getAuthenticatedUser(); 
        if (student != null) {
            model.addAttribute("student", student);
        }

        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("pageUrl", request.getRequestURI());
        return "student-profile";
    }
}
