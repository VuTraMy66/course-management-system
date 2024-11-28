package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Reviews;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Integer> {
    // @Query("SELECT r.courseId, AVG(r.rating) FROM Reviews r GROUP BY r.courseId")
    // List<Object[]> findAverageRatingsByCourse();

    List<Reviews> findByCourse_CourseId(int courseId);
    
    // @Query("SELECT r FROM Reviews r JOIN r.user u WHERE u.id = :userId AND u.role = 'student'")
    // List<Reviews> findReviewsByStudentId(@Param("userId") int userId, String role);
}
