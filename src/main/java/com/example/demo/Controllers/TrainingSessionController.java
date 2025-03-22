package com.example.demo.Controllers;

import com.example.demo.Entities.TrainingSession;
import com.example.demo.services.Interfaces.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class TrainingSessionController {

    @Autowired
    private TrainingSessionService trainingSessionService;

    @GetMapping
    public List<TrainingSession> getAllSessions() {
        return trainingSessionService.getAllSessions();
    }

    @GetMapping("/coach/{coachId}")
    public List<TrainingSession> getSessionsByCoach(@PathVariable Integer coachId) {
        return trainingSessionService.getSessionsByCoach(coachId);
    }

    @PostMapping
    public ResponseEntity<TrainingSession> createSession(@RequestBody TrainingSession session) {
        TrainingSession newSession = trainingSessionService.createSession(session);
        return ResponseEntity.ok(newSession);
    }
}
