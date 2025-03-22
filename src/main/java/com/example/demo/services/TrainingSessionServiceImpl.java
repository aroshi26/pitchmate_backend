package com.example.demo.services;

import com.example.demo.Entities.TrainingSession;
import com.example.demo.Repositories.TrainingSessionRepository;
import com.example.demo.services.Interfaces.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class    TrainingSessionServiceImpl implements TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Override
    public List<TrainingSession> getAllSessions() {
        return trainingSessionRepository.findAll();
    }

    @Override
    public List<TrainingSession> getSessionsByCoach(Integer coachId) {
        return trainingSessionRepository.findByAdmin_StaffId(coachId);
    }

    @Override
    public Optional<TrainingSession> getSessionById(Integer id) {
        return trainingSessionRepository.findById(id);
    }

    @Override
    public TrainingSession createSession(TrainingSession session) {
        return trainingSessionRepository.save(session);
    }

    @Override
    public TrainingSession updateSession(Integer id, TrainingSession session) {
        return trainingSessionRepository.findById(id).map(existingSession -> {
            existingSession.setSessionName(session.getSessionName());
            existingSession.setVenue(session.getVenue());
            existingSession.setConductedBy(session.getConductedBy());
            existingSession.setSessionType(session.getSessionType());
            existingSession.setDescription(session.getDescription());
            existingSession.setSessionDatetime(session.getSessionDatetime());
            existingSession.setPlayers(session.getPlayers());
            return trainingSessionRepository.save(existingSession);
        }).orElseThrow(() -> new RuntimeException("Session not found"));
    }

    @Override
    public void deleteSession(Integer id) {
        if (!trainingSessionRepository.existsById(id)) {
            throw new RuntimeException("Session not found");
        }
        trainingSessionRepository.deleteById(id);
    }
}
