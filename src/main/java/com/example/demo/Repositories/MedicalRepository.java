package com.example.demo.Repositories;

import com.example.demo.Entities.Medical;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicalRepository extends JpaRepository<Medical, Integer> {
    List<Medical> findByPlayer_PlayerId(Integer playerId); // ✅ Custom query
}
