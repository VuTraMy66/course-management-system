package com.example.course_management_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Specializations")
public class Specializations {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialization_id")
    private int specializationId;

    @OneToOne
        @JoinColumn(name = "user_id")
        private Users user;

    @Column(name = "specialization_name")
    private String specializationName;

    public Specializations() {}

    public Specializations(Users user, String specializationName) {
        this.user = user;
        this.specializationName = specializationName;
    }

    public Users getUser() {
        return user;
    }

    public String getSpecializationName() {
        return specializationName;
    }
}