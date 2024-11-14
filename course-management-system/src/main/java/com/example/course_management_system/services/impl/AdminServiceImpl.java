package com.example.course_management_system.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.UsersRepository;
import com.example.course_management_system.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> getAllStudents() {
        return usersRepository.findByRole("user");
    }
}
