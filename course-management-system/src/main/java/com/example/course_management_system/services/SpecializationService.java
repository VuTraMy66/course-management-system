package com.example.course_management_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Specializations;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.repositories.SpecializationRepository;

@Service
public class SpecializationService {
    
    @Autowired
    private SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specializations getSpecialization(int userId) {
        return specializationRepository.findByUser_UserId(userId);
    }
}
