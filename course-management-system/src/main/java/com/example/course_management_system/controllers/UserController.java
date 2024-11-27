package com.example.course_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.course_management_system.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    // @PostMapping("/login")
    // public String handleLogin(@RequestParam("username") String username,
    //                           @RequestParam("password") String password,
    //                           Model model) {
    
    //     return "login";
    // }
}
