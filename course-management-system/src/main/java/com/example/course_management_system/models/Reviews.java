// package com.example.course_management_system.models;

// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "Reviews")

// public class Reviews {
//     @Id

//     @Column(name = "review_id")
//     private int reviewID;

//     @Column(name = "comment")
//     private String comment;

//     @Column(name = "rating")
//     private int rating;

//     @Column(name = "review-date")
//     private LocalDateTime reviewDate;

//     @ManyToOne
//         @JoinColumn(name = "user_id")
//         private int userID;

//     @ManyToOne
//         @JoinColumn(name = "course_id")
//         private int courseID;

//     public Reviews() {}

//     public Reviews(int reviewID, String comment, int rating, LocalDateTime reviewDate, int userID, int courseID){
//         this.reviewID = reviewID;
//         this.comment = comment;
//         this.rating = rating;
//         this.reviewDate = reviewDate;
//         this.userID = userID;
//         this.courseID = courseID;
//     }

//     public int getReviewID(){
//         return reviewID;
//     }
//     public void setReviewID(int reviewID){
//         this.reviewID = reviewID;
//     }

//     public String getComment(){
//         return comment;
//     }
//     public void setComment(String comment){
//         this.comment = comment;
//     }

//     public int getRating(){
//         return rating;
//     }
//     public void setRating(int rating){
//         this.rating = rating;
//     }

//     public LocalDateTime getReviewDate(){
//         return reviewDate;
//     }
//     public void setReviewDate(LocalDateTime reviewDate){
//         this.reviewDate = reviewDate;
//     }

//     public int getUserID(){
//         return userID;
//     }
//     public void setUserID(int userID){
//         this.userID = userID;
//     }

//     public int getCourseID(){
//         return courseID;
//     }
//     public void setCourseID(int courseID){
//         this.courseID = courseID;
//     }

    
// }