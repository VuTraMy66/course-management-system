package com.example.course_management_system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.repositories.CourseRepository;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public CourseService (CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Retrieve all courses
    public List<Courses> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Courses> getAllCourseByCategory(String category) {
        return courseRepository.findByCategory(category);
    }

    //  Retrieve a course by its ID
    // Courses getCourseById(int courseId);

    //  Retrieve courses by name
    // List<Courses> getCoursesByName(String name);

    //  Retrieve courses by category
    // List<Courses> getCoursesByCategory(String category);

    //  Create a new course
    // Courses createCourse(Courses course);

    //  Delete a course by ID
    // void deleteCourse(int courseId);
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
