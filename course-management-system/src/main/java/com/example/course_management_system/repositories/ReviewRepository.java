package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Reviews;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Integer> {
    // @Query("SELECT r FROM Reviews r WHERE r.course.courseId = :courseId")
    // List<Reviews> findByCourseId(@Param("courseId") int courseId);
    
    // @Query("SELECT r FROM Reviews r JOIN r.user u WHERE u.id = :userId AND u.role = 'student'")
    // List<Reviews> findReviewsByStudentId(@Param("userId") int userId, String role);
}
