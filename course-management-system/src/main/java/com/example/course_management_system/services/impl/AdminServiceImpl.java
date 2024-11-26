package com.example.course_management_system.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.EnrollmentRepository;
import com.example.course_management_system.repositories.UsersRepository;
import com.example.course_management_system.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired 
    private UsersRepository userRepository;
    private EnrollmentRepository enrollmentRepository;

    public AdminServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override  
    public List<Users> getAllStudents() {  
        return (List<Users>) userRepository.findByRole("student");  
    }

    @Override
    public void deleteStudent(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<Users> findUserById(int id) {
        throw new UnsupportedOperationException("Unimplemented method 'findUserById'");
    }
}