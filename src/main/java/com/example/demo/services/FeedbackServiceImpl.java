package com.example.demo.services;

import com.example.demo.Entities.Feedback;
import com.example.demo.Entities.Player;
import com.example.demo.Entities.AdminStaff;
import com.example.demo.Repositories.FeedbackRepository;
import com.example.demo.Repositories.PlayerRepository;
import com.example.demo.Repositories.AdminStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AdminStaffRepository adminStaffRepository;

    // ✅ Get all feedback
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // ✅ Get feedback for a specific player
    public List<Feedback> getFeedbackByPlayerId(Integer playerId) {
        Optional<Player> player = playerRepository.findById(playerId);
        return player.map(feedbackRepository::findByPlayer).orElse(null);
    }

    // ✅ Add new feedback
    public Feedback addFeedback(Integer playerId, Integer staffId, String feedbackText) {
        Optional<Player> player = playerRepository.findById(playerId);
        Optional<AdminStaff> staff = adminStaffRepository.findById(staffId);

        if (player.isPresent() && staff.isPresent()) {
            Feedback feedback = new Feedback(player.get(), staff.get(), feedbackText);
            return feedbackRepository.save(feedback);
        } else {
            throw new RuntimeException("Player or Staff not found");
        }
    }
}
