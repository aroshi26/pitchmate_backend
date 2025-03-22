package com.example.demo.Controllers;

import com.example.demo.Entities.Medical;
import com.example.demo.Entities.MedicalRecord;
import com.example.demo.Repositories.MedicalRecordRepository;
import com.example.demo.services.Interfaces.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/medical")
public class MedicalController {

    @Autowired
    private MedicalService medicalService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Update medical record based on user role (Doctor or Psychologist)
    @PutMapping("/update-medical-record/{medicalRecordId}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Integer medicalRecordId,
                                                             @RequestBody MedicalRecord updatedRecord) {

        // Get the authenticated user role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse(null);

        Optional<MedicalRecord> recordOpt = medicalRecordRepository.findById(medicalRecordId);

        if (recordOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MedicalRecord record = recordOpt.get();

        // Role-based updates
        if (userRole.equals("ROLE_Doctor")) {
            // Only doctor can update height and weight
            if (updatedRecord.getHeight() != null) record.setHeight(updatedRecord.getHeight());
            if (updatedRecord.getWeight() != null) record.setWeight(updatedRecord.getWeight());
        } else if (userRole.equals("ROLE_Psychologist")) {
            // Only psychologist can update mental health
            if (updatedRecord.getMentalHealth() != null) record.setMentalHealth(updatedRecord.getMentalHealth());
        }

        medicalRecordRepository.save(record);
        return ResponseEntity.ok(record);
    }

    // Get all medical records
    @GetMapping
    public ResponseEntity<List<Medical>> getAllMedical() {
        return ResponseEntity.ok(medicalService.getAllMedicalRecords());
    }

    // Get medical record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Medical> getMedicalById(@PathVariable Integer id) {
        return medicalService.getMedicalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create medical record
    @PostMapping
    public ResponseEntity<Medical> createMedical(@RequestBody Medical medical) {
        return ResponseEntity.ok(medicalService.createMedical(medical));
    }

    // Update medical record (full update)
    @PutMapping("/{id}")
    public ResponseEntity<Medical> updateMedical(@PathVariable Integer id, @RequestBody Medical medical) {
        return ResponseEntity.ok(medicalService.updateMedical(id, medical));
    }

    // Delete medical record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedical(@PathVariable Integer id) {
        medicalService.deleteMedical(id);
        return ResponseEntity.noContent().build();
    }

    // Get all medical records of a player
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Medical>> getMedicalRecordsByPlayer(@PathVariable Integer playerId) {
        List<Medical> records = medicalService.getMedicalRecordsByPlayerId(playerId);
        return ResponseEntity.ok(records);
    }
}
