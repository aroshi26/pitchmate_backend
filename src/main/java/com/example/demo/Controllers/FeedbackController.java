package com.example.demo.Controllers;

import com.example.demo.Entities.Feedback;
import com.example.demo.Entities.Player;
import com.example.demo.Entities.AdminStaff;
import com.example.demo.Repositories.FeedbackRepository;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Repositories.AdminStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AdminStaffRepository adminStaffRepository;

    // ✅ Get all feedback
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(feedbackRepository.findAll());
    }

    // ✅ Get feedback by player ID
    @GetMapping("/{playerId}")
    public ResponseEntity<List<Feedback>> getFeedbackByPlayer(@PathVariable Integer playerId) {
        Optional<Player> player = playerRepository.findById(playerId);
        if (player.isPresent()) {
            List<Feedback> feedbackList = feedbackRepository.findByPlayer(player.get());
            return ResponseEntity.ok(feedbackList);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/{playerId}")
    public ResponseEntity<?> addFeedback(@PathVariable Integer playerId, @RequestBody Map<String, Object> request) {
        // Validate if required keys exist in the request body
        if (!request.containsKey("staffId") || !request.containsKey("feedback")) {
            return ResponseEntity.badRequest().body("❌ Missing required fields: staffId or feedback.");
        }

        Integer staffId;
        String feedbackText;

        try {
            staffId = Integer.parseInt(request.get("staffId").toString());
            feedbackText = request.get("feedback").toString();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("❌ Invalid data format for staffId.");
        }

        // Fetch Player & Staff
        Optional<Player> player = playerRepository.findById(playerId);
        Optional<AdminStaff> staff = adminStaffRepository.findById(staffId);

        if (player.isEmpty() || staff.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Player or Staff not found.");
        }

        // Create & Save Feedback
        Feedback feedback = new Feedback();
        feedback.setPlayer(player.get());
        feedback.setGivenBy(staff.get());
        feedback.setFeedback(feedbackText);
        feedback.setTimestamp(LocalDateTime.now());

        feedbackRepository.save(feedback);
        return ResponseEntity.ok("✅ Feedback added successfully.");
    }

    // ✅ Add new feedback (with timestamp and sender details)
    @PostMapping
    public ResponseEntity<?> addFeedback(@RequestBody Map<String, Object> request) {
        // ✅ Validate if required keys exist in the request body
        if (!request.containsKey("playerId") || !request.containsKey("staffId") || !request.containsKey("feedback")) {
            return ResponseEntity.badRequest().body("❌ Missing required fields: playerId, staffId, or feedback.");
        }

        Integer playerId;
        Integer staffId;
        String feedbackText;

        try {
            playerId = Integer.parseInt(request.get("playerId").toString());
            staffId = Integer.parseInt(request.get("staffId").toString());
            feedbackText = request.get("feedback").toString();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("❌ Invalid data format for playerId or staffId.");
        }

        // ✅ Fetch Player & Staff
        Optional<Player> player = playerRepository.findById(playerId);
        Optional<AdminStaff> staff = adminStaffRepository.findById(staffId);

        if (player.isEmpty() || staff.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Player or Staff not found.");
        }

        // ✅ Create & Save Feedback
        Feedback feedback = new Feedback();
        feedback.setPlayer(player.get());
        feedback.setGivenBy(staff.get());
        feedback.setFeedback(feedbackText);
        feedback.setTimestamp(LocalDateTime.now());

        feedbackRepository.save(feedback);
        return ResponseEntity.ok("✅ Feedback added successfully.");
    }

}
