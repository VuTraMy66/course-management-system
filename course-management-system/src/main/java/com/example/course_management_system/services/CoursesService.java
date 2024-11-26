package com.example.course_management_system.services;

import java.util.List;

import com.example.course_management_system.models.Courses;

public interface CoursesService {

    // Retrieve all courses
    List<Courses> getAllCourses();

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


