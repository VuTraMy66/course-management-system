package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.LessonRepository;
import com.example.course_management_system.repositories.UsersRepository;

@Service
public class UserService {
    @Autowired
    public UsersRepository userRepository;

    public UserService (UsersRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);  
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

    // public Users handleUserLogin(String username, String password) {
    //     Users user = userRepository.findByUsername(username);
        
    //     if (user != null && user.getPassword().equals(password)) {
    //         return user;
    //     }
    //     return null;
    // }
}
