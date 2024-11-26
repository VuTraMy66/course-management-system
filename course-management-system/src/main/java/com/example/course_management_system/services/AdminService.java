package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.UsersRepository;

@Service
public class AdminService {
    @Autowired
    private UsersRepository userRepository;
    // private EnrollmentRepository enrollmentRepository;

    public AdminService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    // View student
    // Fetch all students with role "student"
    public List<Users> getAllStudents() {
        return (List<Users>) userRepository.findByRole("student");
    }

    // Delete a student by their user ID
    public void deleteStudent(int userId) {
        userRepository.deleteById(userId);
    }

    // Find a user by their ID
    public Optional<Users> findUserById(int id) {
        return userRepository.findById(id);
    }

    // Course

    // List<Reviews> getAllStudentsAll(); 
}

