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
@Table(name = "Reviews")

public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @ManyToOne
        @JoinColumn(name = "user_id")
        private Users user;

    @ManyToOne
        @JoinColumn(name = "course_id")
        private Courses course;

    public Reviews() {
        this.reviewDate = LocalDateTime.now();
    }

    public Reviews(int reviewId, String comment, int rating, Users user, Courses course){
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.course = course;
    }

    public int getReviewID(){
        return reviewId;
    }

    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public int getRating(){
        if(rating < 1 || rating > 5){
            throw new IllegalArgumentException("Rating must between 1 and 5!");
        }
        return rating;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public String getFormattedReviewDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return reviewDate.format(formatter);
    }

    public String getFormattedReviewDate2(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return reviewDate.format(formatter);
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