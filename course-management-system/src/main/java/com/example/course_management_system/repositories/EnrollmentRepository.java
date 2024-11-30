package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Enrollments;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollments, Integer> {
    
    List<Enrollments> findByCourse_CourseId(int courseId);

    List<Enrollments> findByUser_UserId(int userId);

    List<Enrollments> findByUser_UserIdAndStatus(@Param("userId") int userId, @Param("status") String status);

}
