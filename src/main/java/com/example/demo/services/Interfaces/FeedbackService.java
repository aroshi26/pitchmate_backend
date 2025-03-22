package com.example.demo.services.Interfaces;

import com.example.demo.Entities.Feedback;
import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<Feedback> getAllFeedback();
    Optional<Feedback> getFeedbackById(Integer id);
    Feedback createFeedback(Feedback feedback);
    Feedback updateFeedback(Integer id, Feedback feedback);
    void deleteFeedback(Integer id);
}
