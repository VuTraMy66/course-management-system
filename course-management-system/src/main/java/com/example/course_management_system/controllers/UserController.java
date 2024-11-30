package com.example.course_management_system.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AuthService;
import com.example.course_management_system.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


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

    @GetMapping("/student/profile")
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

    @PostMapping("/student/update-profile")
    public String updateStudentProfile(@ModelAttribute("user") Users user, @RequestParam("userId") int userId, HttpSession session) {
        Optional<Users> existingUser = userService.findUserById(userId);
        if (existingUser.isPresent()) {
            Users userToUpdate = existingUser.get();
    
            userToUpdate.setFirstname(user.getFirstname());
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhone(user.getPhone());
    
            userService.saveUser(userToUpdate);

        }

         return "redirect:/student/profile";
    }
}
