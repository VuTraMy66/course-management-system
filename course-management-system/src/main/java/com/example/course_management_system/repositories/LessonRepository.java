package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Lessons;

@Repository
public interface LessonRepository extends JpaRepository<Lessons, Integer> {

    List<Lessons> findByCourse_CourseId(int courseId);

       
    // // Delete a session by its ID (provided by deleteById method)
    // void deleteById(Integer sessionId);
}
