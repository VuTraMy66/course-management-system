package com.example.course_management_system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.repositories.EnrollmentRepository;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollments> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollments> getEnrollmentsByCourseId(int courseId) {
        return enrollmentRepository.findByCourse_CourseId(courseId);
    }

    public List<Enrollments> getEnrollmentsByUserId(@Param("userId") int userId) {
        return enrollmentRepository.findByUser_UserId(userId);
    }

    
}
