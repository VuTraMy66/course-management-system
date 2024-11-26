package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Users;

public interface AdminService {
    // Student
    List<Users> getAllStudents();

    void deleteStudent(int userId);

    // Course

    // void saveUser(User user);  

    // void deleteUser(Long id);  

    Optional<Users> findUserById(int id);

<<<<<<< HEAD
    List<Courses> getAllCourses(); 
=======
    // List<Reviews> getAllStudentsAll(); 
>>>>>>> 435172488850d45cf74358ebb87e287118a98c6f
}

