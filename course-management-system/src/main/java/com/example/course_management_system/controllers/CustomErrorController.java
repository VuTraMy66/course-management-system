package com.example.course_management_system.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}
