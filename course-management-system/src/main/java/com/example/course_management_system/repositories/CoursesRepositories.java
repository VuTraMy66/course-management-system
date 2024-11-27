package com.example.course_management_system.repositories;

import java.util.List;
import java.util.Optional;

//import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.course_management_system.models.Courses;


@Repository
public interface CoursesRepositories extends JpaRepository<Courses, Integer> {

    // @Query("SELECT c FROM Course c WHERE c.name = :name")
    // List<Courses> findByRole(@Param("name") String name);

    List<Courses> findByCategory(String category);

    List<Courses> findByName(String name);

    Optional<Courses> findById(int courseId);
}
