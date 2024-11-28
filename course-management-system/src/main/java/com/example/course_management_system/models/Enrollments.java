package com.example.course_management_system.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Enrollments")
public class Enrollments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private int enrollId;

    @Column(name = "enroll_at")
    private LocalDateTime enrollAt;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
        @JoinColumn(name = "user_id")
        private Users user;

    @ManyToOne
        @JoinColumn(name = "course_id")
        private Courses course;

    public Enrollments() {
        this.enrollAt = LocalDateTime.now();
        this.status = "learning";
    }

    public Enrollments(int enrollId, String status, Users user, Courses course) {
        this.enrollId = enrollId;
        this.status = status;
        this.user = user;
        this.course = course;
    }

    public int getEnrollId() {
        return enrollId;
    }

    public LocalDateTime getEnrollAt() {
        return enrollAt;
    }

    public String getFormattedEnrollAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return enrollAt.format(formatter);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }
}
