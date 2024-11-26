package com.example.course_management_system.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.repositories.CoursesRepositories;
import com.example.course_management_system.services.CoursesService;

@Service
public class CoursesServiceImpl implements CoursesService {

    @Autowired
    private CoursesRepositories coursesRepositories;

    @Override
    public List<Courses> getAllCourses() {
        return coursesRepositories.findAll();
    }

    // @Override
    //  public Courses getCourseById(int courseId) {
    //     throw new UnsupportedOperationException("Unimplemented method 'getCourseById'");

    //  }
        
    // @Override
    // public List<Courses> getCoursesByName(String name) {
    //     return coursesRepositories.findByName(name);
    // }

    // @Override
    // public List<Courses> getCoursesByCategory(String category) {
    //     return coursesRepositories.findByCategory(category);
    // }

    // @Override
    // public Courses createCourse(Courses course) {
    //     return coursesRepositories.save(course);
    // }

    // @Override
    // public void deleteCourse(int courseId) {
    //     if (!coursesRepositories.existsById(courseId)) {
    //         throw new RuntimeException("Course not found with ID: " + courseId);
    //     }
    //     coursesRepositories.deleteById(courseId);
    // }
}

