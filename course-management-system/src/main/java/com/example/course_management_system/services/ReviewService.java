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

    // public List<Reviews> getReviewsByCourseId(int courseId) {
    //     return reviewRepository.findByCourseId(courseId);
    // }

    // public List<Reviews> getReviewsByStudentId(int userId) {
    //     return reviewRepository.findReviewsByStudentId(userId);
    // }
}
