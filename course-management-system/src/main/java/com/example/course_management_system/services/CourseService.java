package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Categories;
import com.example.course_management_system.models.Courses;
import com.example.course_management_system.repositories.CourseRepository;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public CourseService (CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Courses> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Courses> getAllCourseByCategory(Categories category) {
        return courseRepository.findByCategory(category);
    }

    public Optional<Courses> getCourseById(int coureId) {
        return courseRepository.findById(coureId);
    }

    public void saveCourse(Courses course) {
        courseRepository.save(course);
    }

    public void deleteCourseById(int courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Courses> getCourseByInstructor(int userId) {
        return courseRepository.findByUser_UserId(userId);
    }
}
