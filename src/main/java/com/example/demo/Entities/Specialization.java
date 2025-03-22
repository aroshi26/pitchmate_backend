package com.example.demo.Entities;

import jakarta.persistence.*;


@Entity
@Table(name = "Specializations")
public class Specialization {

    @Id
    private Integer specializationId;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    @Column(nullable = false)
    private String specializationName;

    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }
}
