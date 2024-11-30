package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Reviews;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Integer> {

    List<Reviews> findByCourse_CourseId(int courseId);

    int countByCourse_CourseId(int courseId); 

}
