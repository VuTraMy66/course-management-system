package com.example.course_management_system.controllers;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
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

    // @DeleteMapping("/delete-course/{courseId}")
    // public ResponseEntity<String> deleteCourse(@PathVariable int courseId) {
    //     try {
    //         coursesService.deleteCourse(courseId);
    //         return ResponseEntity.ok("Course deleted successfully.");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
    //     }
    // }
}


