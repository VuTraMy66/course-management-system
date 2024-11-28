package com.example.course_management_system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Reviews;
import com.example.course_management_system.repositories.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService (ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Reviews> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Reviews> getReviewsByCourseId(int courseId) {
        return reviewRepository.findByCourse_CourseId(courseId);
    }

    public double calculateAverageRating(int courseId) {
        List<Reviews> courseReviews = reviewRepository.findByCourse_CourseId(courseId);
        if (courseReviews.isEmpty()) {
            return 0.0;
        }
        
        double totalRating = 0.0;
        for (Reviews review : courseReviews) {
            totalRating += review.getRating();
        }
        
        double average = totalRating / courseReviews.size();
        return Math.round(average * 10.0) / 10.0;
    }

    // public List<Reviews> getReviewsByStudentId(int userId) {
    //     return reviewRepository.findReviewsByStudentId(userId);
    // }
}
