package com.example.demo.services;

import com.example.demo.Entities.Medical;
import com.example.demo.Repositories.MedicalRepository;
import com.example.demo.services.Interfaces.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalServiceImpl implements MedicalService {

    @Autowired
    private MedicalRepository medicalRepository;

    @Override
    public List<Medical> getAllMedicalRecords() {
        return medicalRepository.findAll();
    }

    @Override
    public Optional<Medical> getMedicalById(Integer id) {
        return medicalRepository.findById(id);
    }

    @Override
    public Medical createMedical(Medical medical) {
        return medicalRepository.save(medical);
    }

    @Override
    public Medical saveMedicalRecord(Medical medical) {
        return medicalRepository.save(medical);
    }

    @Override
    public Medical updateMedical(Integer id, Medical medical) {
        return medicalRepository.findById(id).map(existing -> {
            existing.setPlayer(medical.getPlayer());
            existing.setAppointmentDate(medical.getAppointmentDate());
            existing.setAppointmentTime(medical.getAppointmentTime());
            existing.setStaff(medical.getStaff());
            existing.setPurpose(medical.getPurpose());
            existing.setDescription(medical.getDescription());
            existing.setMedicalRecord(medical.getMedicalRecord());
            return medicalRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Medical record not found"));
    }

    @Override
    public void deleteMedical(Integer id) {
        medicalRepository.deleteById(id);
    }

    @Override
    public List<Medical> getMedicalRecordsByPlayerId(Integer playerId) {
        return medicalRepository.findByPlayer_PlayerId(playerId);
    }
}
