package com.example.course_management_system.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_management_system.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByRole(String role);
}
