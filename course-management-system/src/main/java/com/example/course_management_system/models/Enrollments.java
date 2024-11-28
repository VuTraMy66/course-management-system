// package com.example.course_management_system.models;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

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
//     @Column(name = "enroll_id")
//     private int enrollId;

    
//         @Column(name = "user_id")
//         private Users user;

    
//         @Column(name = "course_id")
//         private Courses course;

//     @Column(name = "enroll_at")
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

//     public int getEnrollId() {
//         return enrollId;
//     }

//     public Users getUser() {
//         return user;
//     }

//     public Courses getCourse() {
//         return course;
//     }

//     public String getFormattedEnrollAt() {
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
//         return enrollAt.format(formatter);
//     }

//     public void setEnrollAt(LocalDateTime enrollAt) {
//         this.enrollAt = LocalDateTime.now();
//     }

//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }
// }
