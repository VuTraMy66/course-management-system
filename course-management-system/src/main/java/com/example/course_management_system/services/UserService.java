package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.UsersRepository;

@Service
public class UserService {
    @Autowired
    public UsersRepository userRepository;

    public UserService (UsersRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);  
    }

    public List<Users> getAllStudents() {
        return (List<Users>) userRepository.findByRole("student");
    }

    public void deleteStudent(int userId) {
        userRepository.deleteById(userId);
    }

    public Optional<Users> findUserById(int id) {
        return userRepository.findById(id);
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }

}
