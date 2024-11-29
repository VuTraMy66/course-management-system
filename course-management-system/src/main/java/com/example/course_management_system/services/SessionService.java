package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Sessions;
import com.example.course_management_system.repositories.SessionRepository;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public SessionService (SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<Sessions> getAllSessionsOfCourse(int courseId) {
        return sessionRepository.findSessionsByCourseId(courseId);
    }

    // public 
    // void deleteSession(int sessionId);  //delete session

    // void getSessionById(int sessionId);  

    // void createSession(Sessions session);

    // void updateSession(Sessions session);

    // Optional<Sessions> finSessionById(int SessionId);  

}
