package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name = "MedicalRecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicalRecordId;

    private BigDecimal height;

    private BigDecimal weight;

    private String mentalHealth;

    @Lob
    private String description;

    // Getters and setters
    public Integer getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(Integer medicalRecordId) { this.medicalRecordId = medicalRecordId; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public String getMentalHealth() { return mentalHealth; }
    public void setMentalHealth(String mentalHealth) { this.mentalHealth = mentalHealth; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

