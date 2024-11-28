package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Sessions;

@Service
public interface SessionService {

    List <Sessions> getAllSessions();

    void deleteSession(int sessionId);  //delete session

    void getSessionById(int sessionId);  

    void createSession(Sessions session);

    void updateSession(Sessions session);

    Optional<Sessions> finSessionById(int SessionId);  

}
