// package com.example.course_management_system.models;

// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "Enrollments")
// public class Enrollments {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "enrollId")
//     private int enrollId;

//     private Users user;

//     private Courses course;

//     @Column(name = "enrollAt")
//     private LocalDateTime enrollAt;

//     @Column(name = "status")
//     private String status;

//     public Enrollments() {}

//     public Enrollments(Users user, Courses course, LocalDateTime enrollAt, String status) {
//         this.user = user;
//         this.course = course;
//         this.enrollAt = enrollAt;
//         this.status = status;
//     }

//     public Users getUser() {
//         return user;
//     }

//     public Courses getCourse() {
//         return course;
//     }

//     public LocalDateTime getEnrollAt() {
//         return enrollAt;
//     }

//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }
// }
