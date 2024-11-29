package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Sessions;

@Repository
public interface SessionRepository extends JpaRepository<Sessions, Integer> {

    @Query("SELECT s FROM Sessions s WHERE s.course.courseId = :courseId")
    List<Sessions> findSessionsByCourseId(Integer courseId);
       
    // // Fetch all sessions
    // List<Sessions> findAll();
    
    // // Find a session by its ID (optional return type in case it doesn't exist)
    // Optional<Sessions> findById(Integer sessionId);
       
    // // Find sessions by course ID (custom query if you want to fetch sessions by course)
    // List<Sessions> findByCourseId(Integer courseId);
       
    // // Delete a session by its ID (provided by deleteById method)
    // void deleteById(Integer sessionId);
}
