package com.example.course_management_system.services;

import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.UsersRepository;

@Service
public class UserService {
    public final UsersRepository userRepository;

    public UserService (UsersRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);  
    }

    public Users handleUserLogin(String username, String password) {
        Users user = userRepository.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
