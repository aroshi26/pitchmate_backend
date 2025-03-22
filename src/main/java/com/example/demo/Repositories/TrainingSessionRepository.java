package com.example.demo.Repositories;

import com.example.demo.Entities.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Integer> {
    List<TrainingSession> findByAdmin_StaffId(Integer staffId);  // ✅ Filter sessions by coach ID
}
