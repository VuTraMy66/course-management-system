package com.example.course_management_system.models;

import java.time.LocalDateTime;

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
    @Column(name = "reviewId")
    private int reviewId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private int rating;

    @Column(name = "reviewDate")
    private LocalDateTime reviewDate = LocalDateTime.now();

    @ManyToOne
        @JoinColumn(name = "userId")
        private int userId;

    @ManyToOne
        @JoinColumn(name = "courseId")
        private int courseId;

    public Reviews() {}

    public Reviews(int reviewId, String comment, int rating, LocalDateTime reviewDate, int userId, int courseId){
        this.reviewId = reviewId;
        this.comment = comment;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getReviewID(){
        return reviewId;
    }
    public void setReviewID(int reviewID){
        this.reviewId = reviewID;
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

    public LocalDateTime getReviewDate(){
        return reviewDate;
    }
    public void setReviewDate(LocalDateTime reviewDate){
        this.reviewDate = reviewDate;
    }

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getCourseId(){
        return courseId;
    }
    public void setCourseId(int courseId){
        this.courseId = courseId;
    }

}