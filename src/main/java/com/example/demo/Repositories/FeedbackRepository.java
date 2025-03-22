package com.example.demo.Repositories;

import com.example.demo.Entities.Feedback;
import com.example.demo.Entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByPlayer(Player player);
}
