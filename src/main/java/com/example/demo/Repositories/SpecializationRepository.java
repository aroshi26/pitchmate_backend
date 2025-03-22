package com.example.demo.Repositories;

import com.example.demo.Entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {
    // Custom query methods if needed
}