package com.example.demo.Repositories;

import com.example.demo.Entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {
    // Custom query methods if needed
}