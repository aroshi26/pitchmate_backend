package com.example.demo.services.Interfaces;


import com.example.demo.Entities.Medical;
import java.util.List;
import java.util.Optional;

public interface MedicalService {
    List<Medical> getAllMedicalRecords();
    Optional<Medical> getMedicalById(Integer id);
    Medical createMedical(Medical medical);
    Medical updateMedical(Integer id, Medical medical);
    Medical saveMedicalRecord(Medical medical);
    void deleteMedical(Integer id);

    List<Medical> getMedicalRecordsByPlayerId(Integer playerId);
}
