package com.example.course_management_system.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.repositories.EnrollmentRepository;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollments> getEnrollmentsByCourseId(int courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
}
