package com.example.demo.services.Interfaces;

import com.example.demo.Entities.TrainingSession;
import java.util.List;
import java.util.Optional;

public interface TrainingSessionService {
    List<TrainingSession> getAllSessions();
    Optional<TrainingSession> getSessionById(Integer id);
    TrainingSession createSession(TrainingSession session);
    TrainingSession updateSession(Integer id, TrainingSession session);
    void deleteSession(Integer id);
    List<TrainingSession> getSessionsByCoach(Integer coachId);
}
