package com.example.course_management_system.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Courses;


@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer> {

    List<Courses> findByCategory(String category);

    Optional<Courses> findById(int coureId);

    @Query("""
        SELECT c 
        FROM Courses c 
        JOIN Reviews r ON c.id = r.course.id 
        WHERE c.category IN :categories 
          AND c.skillLevel IN :skillLevels 
        GROUP BY c.id 
        HAVING AVG(r.rating) >= :rating
    """)
    List<Courses> findFilteredCourses(
        @Param("categories") List<String> categories,
        @Param("rating") double rating,
        @Param("skillLevels") List<String> skillLevels
    );
}
