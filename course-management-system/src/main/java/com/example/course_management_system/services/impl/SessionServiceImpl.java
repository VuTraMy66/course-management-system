package com.example.course_management_system.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.repositories.SessionRepository;
import com.example.course_management_system.services.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public void deleteSession(int sessionId){
        sessionRepository.deleteById(sessionId);
    }
}
