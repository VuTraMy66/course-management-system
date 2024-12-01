package com.example.course_management_system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.course_management_system.models.Categories;
import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.models.Reviews;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AuthService;
import com.example.course_management_system.services.CategoryService;
import com.example.course_management_system.services.CourseService;
import com.example.course_management_system.services.EnrollmentService;
import com.example.course_management_system.services.LessonService;
import com.example.course_management_system.services.ReviewService;
import com.example.course_management_system.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired 
    private AuthService authService;
    private CourseService courseService;
    private ReviewService reviewService;
    private EnrollmentService enrollmentService;
    private LessonService lessonService;
    private UserService userService;
    private CategoryService categoryService;
    
    public HomeController(AuthService authService, CourseService courseService, ReviewService reviewService, EnrollmentService enrollmentService, LessonService lessonService, UserService userService, CategoryService categoryService) {
        this.courseService = courseService;
        this.reviewService = reviewService;
        this.enrollmentService = enrollmentService;
        this.lessonService = lessonService;
        this.authService = authService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping({"/home", "/"})
    public String viewHomePage(Model model) {
        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        List<Courses> courses = courseService.getAllCourses();

        Map<Integer, Double> courseRatings = new HashMap<>();
        Map<Integer, Integer> courseReviewCounts = new HashMap<>();

        for (Courses course : courses) {
            double averageRating = reviewService.calculateAverageRating(course.getCourseId());
            courseRatings.put(course.getCourseId(), averageRating);

            int reviewCount = reviewService.getReviewCount(course.getCourseId());
            courseReviewCounts.put(course.getCourseId(), reviewCount);

            course.setReviewCount(reviewCount);
            course.setAverageRating(averageRating);
        }

        List<Categories> allCategories = categoryService.getAllCategories();

        int courseCountCategory1 = 0;
        int courseCountCategory2 = 0;
        int courseCountCategory3 = 0;
        int courseCountCategory4 = 0;
        int courseCountCategory5 = 0;

        if (allCategories.size() >= 5) {
            courseCountCategory1 = courseService.getAllCourseByCategory(allCategories.get(0)).size();
            courseCountCategory2 = courseService.getAllCourseByCategory(allCategories.get(1)).size();
            courseCountCategory3 = courseService.getAllCourseByCategory(allCategories.get(2)).size();
            courseCountCategory4 = courseService.getAllCourseByCategory(allCategories.get(3)).size();
            courseCountCategory5 = courseService.getAllCourseByCategory(allCategories.get(4)).size();
        }

        List<Courses> filteredCourses = courses.stream()
            .filter(course -> courseRatings.get(course.getCourseId()) > 3.0) 
            .collect(Collectors.toList());

        List<Courses> topRatedCourses = filteredCourses.stream().limit(8).collect(Collectors.toList());

        List<Reviews> reviews = reviewService.getAllReviews();

        List<Reviews> sortedReviews = reviews.stream()
            .sorted((review1, review2) -> Double.compare(review2.getRating(), review1.getRating()))
            .collect(Collectors.toList());

        List<Reviews> topReviews = sortedReviews.stream().limit(2).collect(Collectors.toList());

        double averageRating = reviews.stream()
            .mapToDouble(Reviews::getRating)  
            .average()
            .orElse(0.0); 

        int totalReviews = reviews.size();

        List<Users> users = userService.getAllStudents("student");

        Map<Integer, Integer> totalCoursesOfEachStudent = new HashMap<>();

        for (Users user : users) {
            List<Enrollments> enrollments = enrollmentService.getEnrollmentsByUserId(user.getUserId());
            totalCoursesOfEachStudent.put(user.getUserId(), enrollments.size());
        }

        List<Users> sortedUsers = users.stream()
            .sorted((u1, u2) -> Integer.compare(
                totalCoursesOfEachStudent.get(u2.getUserId()),
                totalCoursesOfEachStudent.get(u1.getUserId())
                ))
            .limit(3)
            .collect(Collectors.toList());

        model.addAttribute("courseCountCategory1", courseCountCategory1);
        model.addAttribute("courseCountCategory2", courseCountCategory2);
        model.addAttribute("courseCountCategory3", courseCountCategory3);
        model.addAttribute("courseCountCategory4", courseCountCategory4);
        model.addAttribute("courseCountCategory5", courseCountCategory5);
        model.addAttribute("category", allCategories);
        model.addAttribute("courses", topRatedCourses);
        model.addAttribute("courseRatings", courseRatings);
        model.addAttribute("courseReviewCounts", courseReviewCounts);
        model.addAttribute("topReviews", topReviews);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("totalReviews", totalReviews);
        model.addAttribute("users", sortedUsers);

        return "index";
    }

    // @RequestMapping("/logout")
    // public String logout() {
    //     return "redirect:/login?logout"; // Redirect back to login page after logout
    // }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @RequestMapping("/student/dashboard")
    public String studentDashboard(HttpServletRequest request, Model model) {

        Users student = AuthenticationUtils.getAuthenticatedUser(); 
        if (student != null) {
            model.addAttribute("student", student);
        }

        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("pageUrl", request.getRequestURI());
        return "student-dashboard";
    }

    @RequestMapping("/student/review")
    public String studentReview(HttpServletRequest request, Model model) {

        Users student = AuthenticationUtils.getAuthenticatedUser(); 
        if (student != null) {
            model.addAttribute("student", student);
        }

        boolean isAuthenticated = authService.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("pageUrl", request.getRequestURI());
        return "student-review";
    }
    
}
