package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Medical")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicalId;

    @ManyToOne
    @JoinColumn(name = "playerId", nullable = false)
    private Player player;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @ManyToOne
    @JoinColumn(name = "staffId", nullable = false)
    private AdminStaff staff;

    @Column(nullable = false, length = 100)
    private String purpose;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "medicalRecordId")
    private MedicalRecord medicalRecord;

    @Column(nullable = true, length = 1000)
    private String doctorNotes;

    // Getters and setters
    public Integer getMedicalId() { return medicalId; }
    public void setMedicalId(Integer medicalId) { this.medicalId = medicalId; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public AdminStaff getStaff() { return staff; }
    public void setStaff(AdminStaff staff) { this.staff = staff; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MedicalRecord getMedicalRecord() { return medicalRecord; }
    public void setMedicalRecord(MedicalRecord medicalRecord) { this.medicalRecord = medicalRecord; }

    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }
}

