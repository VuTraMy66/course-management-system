package com.example.course_management_system.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.UsersRepository;

@Service
public class AccountService {
    @Autowired
    private UsersRepository userRepository;

    public AccountService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users login(String username, String en_password){
        Users u = userRepository.findByUsername(username);
        if (u != null && en_password.equals(u.getUsername())){
            return u;
        } 
        return null;
    }

    public boolean register(Users user){
        try{
            userRepository.addUser(user);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}