package com.example.course_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Specializations;


@Repository
public interface SpecializationRepository extends JpaRepository<Specializations, Integer> {
    Specializations findByUser_UserId(int userId);
}
