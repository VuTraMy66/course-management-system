package com.example.course_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.course_management_system.models.Sessions;

@Repository
public interface SessionRepository extends JpaRepository<Sessions, Integer> {
    
}
