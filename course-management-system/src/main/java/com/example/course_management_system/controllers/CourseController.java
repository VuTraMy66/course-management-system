package com.example.course_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.services.CoursesService;

@RestController
// @RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping
    public List<Courses> getAllCourses() {
        return coursesService.getAllCourses();
    }


    @GetMapping
    public Optional<Courses> getCourseById(@PathVariable int courseId) {
        return coursesService.getCourseById(courseId);
    }
}


