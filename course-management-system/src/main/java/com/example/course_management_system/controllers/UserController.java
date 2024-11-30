package com.example.course_management_system.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AuthService;
import com.example.course_management_system.services.EnrollmentService;
import com.example.course_management_system.services.ReviewService;
import com.example.course_management_system.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private AuthService authService;
    private UserService userService;
    private EnrollmentService enrollmentService;
    private ReviewService reviewService;

    public UserController(AuthService authService, UserService userService, EnrollmentService enrollmentService, ReviewService reviewService) {
        this.userService = userService;
        this.authService = authService;
        this.enrollmentService = enrollmentService;
        this.reviewService = reviewService;
    }

    @GetMapping("/student")
    public String student(Model model) {
        Users student = AuthenticationUtils.getAuthenticatedUser(); 
        if (student != null) {
            model.addAttribute("student", student);
        }

        int userId = student.getUserId();

        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        List<Enrollments> learningCourses = enrollmentService.getEnrollmentsByUserIdWithStatus(userId, "learning");
        List<Courses> learnings = new ArrayList<>();
        for (Enrollments enrollment : learningCourses) {
            learnings.add(enrollment.getCourse());
        }

        Map<Integer, Double> learningRatings = new HashMap<>();
        Map<Integer, Integer> learningReviews = new HashMap<>();

        for (Courses learning : learnings) {
            double averageRating = reviewService.calculateAverageRating(learning.getCourseId());
            learningRatings.put(learning.getCourseId(), averageRating);

            int reviewCount = reviewService.getReviewCount(learning.getCourseId());
            learningReviews.put(learning.getCourseId(), reviewCount);

            learning.setReviewCount(reviewCount);
            learning.setAverageRating(averageRating);
        }

        List<Enrollments> completedCourses = enrollmentService.getEnrollmentsByUserIdWithStatus(userId, "completed");
        List<Courses> completeds = new ArrayList<>();
        for (Enrollments enrollment : completedCourses) {
            completeds.add(enrollment.getCourse());
        }
        
        Map<Integer, Double> completedRatings = new HashMap<>();
        Map<Integer, Integer> completedReviews = new HashMap<>();

        for (Courses completed : completeds) {
            double averageRating = reviewService.calculateAverageRating(completed.getCourseId());
            completedRatings.put(completed.getCourseId(), averageRating);

            int reviewCount = reviewService.getReviewCount(completed.getCourseId());
            completedReviews.put(completed.getCourseId(), reviewCount);

            completed.setReviewCount(reviewCount);
            completed.setAverageRating(averageRating);
        }

        List<Enrollments> droppedCourses = enrollmentService.getEnrollmentsByUserIdWithStatus(userId, "dropped");
        List<Courses> droppeds = new ArrayList<>();
        for (Enrollments enrollment : droppedCourses) {
            droppeds.add(enrollment.getCourse());
        }
        
        Map<Integer, Double> droppedRatings = new HashMap<>();
        Map<Integer, Integer> droppedReviews = new HashMap<>();

        for (Courses dropped : droppeds) {
            double averageRating = reviewService.calculateAverageRating(dropped.getCourseId());
            droppedRatings.put(dropped.getCourseId(), averageRating);

            int reviewCount = reviewService.getReviewCount(dropped.getCourseId());
            droppedReviews.put(dropped.getCourseId(), reviewCount);

            dropped.setReviewCount(reviewCount);
            dropped.setAverageRating(averageRating);
        }

        model.addAttribute("learnings", learnings);
        model.addAttribute("learningRatings", learningRatings);
        model.addAttribute("learningReviews", learningReviews);
        model.addAttribute("completeds", completeds);
        model.addAttribute("completedRatings", completedRatings);
        model.addAttribute("completedReviews", completedReviews);
        model.addAttribute("droppeds", droppeds);
        model.addAttribute("droppedRatings", droppedRatings);
        model.addAttribute("droppedReviews", droppedReviews);

        return "student";
    }

    @GetMapping("/student/profile")
    public String studentProfile(HttpServletRequest request, Model model) {
        Users student = AuthenticationUtils.getAuthenticatedUser(); 
        if (student != null) {
            model.addAttribute("student", student);
        }

        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("pageUrl", request.getRequestURI());
        return "student-profile";
    }

    @PostMapping("/student/update-profile")
    public String updateStudentProfile(@ModelAttribute("user") Users user, @RequestParam("userId") int userId, HttpSession session) {
        Optional<Users> existingUser = userService.findUserById(userId);
        if (existingUser.isPresent()) {
            Users userToUpdate = existingUser.get();
    
            userToUpdate.setFirstname(user.getFirstname());
            userToUpdate.setLastname(user.getLastname());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhone(user.getPhone());
    
            userService.saveUser(userToUpdate);

        }

         return "redirect:/student/profile";
    }
}
