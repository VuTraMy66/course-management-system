package com.example.course_management_system.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.models.Reviews;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AdminService;
import com.example.course_management_system.services.CourseService;
import com.example.course_management_system.services.EnrollmentService;
import com.example.course_management_system.services.ReviewService;
import com.example.course_management_system.services.UserService;

@Controller
public class AdminController {

    @Autowired 
    private AdminService adminService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private ReviewService reviewService;
    private UserService userService;

    public AdminController(AdminService adminService, CourseService courseService, EnrollmentService enrollmentService, ReviewService reviewService, UserService userService) {
        this.adminService = adminService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public String showAdminDashboard(Model model) {
        List<Courses> courses = courseService.getAllCourses();
        List<Users> users = userService.getAllStudents();

        int totalCourses = courses.size();
        int totalStudents = users.size();

        users.sort((user1, user2) -> Integer.compare(user2.getUserId(), user1.getUserId()));

        // Limit the list to 8 users
        List<Users> topUsers = users.stream().limit(8).collect(Collectors.toList());


        model.addAttribute("users", topUsers);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("pageUrl", "/admin");
        return "admin"; 
    }

    // Show all courses
    @GetMapping("/admin/courses")
    public String adminAllCourse(Model model) {
        try {
            List<Courses> coursesInAd = courseService.getAllCourses();
            
            Map<Integer, Integer> totalStudentsPerCourse = new HashMap<>();

            for (Courses course : coursesInAd) {
                List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
                totalStudentsPerCourse.put(course.getCourseId(), enrollments.size());
            }

            model.addAttribute("coursesInAd", coursesInAd);
            model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);

            return "admin-all-course";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching courses or enrollments.");
            return "error-page";
        }
    }

    @GetMapping("/admin/course-category")
    public String adminCourseCategory(Model model) {
        List<Courses> coursesOfP = courseService.getAllCourseByCategory("Programming");
        List<Courses> coursesOfDS = courseService.getAllCourseByCategory("Data Science");
        List<Courses> coursesOfUD = courseService.getAllCourseByCategory("UI/UX Design");
        List<Courses> coursesOfWD = courseService.getAllCourseByCategory("Web Development");
        List<Courses> coursesOfAI = courseService.getAllCourseByCategory("Artificial Intelligence");

        int totalP = coursesOfP.size();
        int totalDS = coursesOfDS.size();
        int totalUD = coursesOfUD.size();
        int totalWD = coursesOfWD.size();
        int totalAI = coursesOfAI.size();

        model.addAttribute("totalP", totalP);
        model.addAttribute("totalDS", totalDS);
        model.addAttribute("totalUD", totalUD);
        model.addAttribute("totalWD", totalWD);
        model.addAttribute("totalAI", totalAI);
        model.addAttribute("pageUrl", "/admin/course-category");
        return "admin-course-category"; 
    }

    @GetMapping("/admin/course-category/{category}")
    public String adminCourseCategoryDetail(@PathVariable("category") String category, Model model) {
        String formattedCategory = convertCategoryFormat(category);
        List<Courses> coursesCategory = courseService.getAllCourseByCategory(formattedCategory);

        Map<Integer, Integer> totalStudentsPerCourse = new HashMap<>();

        for (Courses course : coursesCategory) {
            List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
            totalStudentsPerCourse.put(course.getCourseId(), enrollments.size());
        }

        int courseCount = coursesCategory.size();

        model.addAttribute("coursesCategory", coursesCategory);
        model.addAttribute("category", formattedCategory);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);
        model.addAttribute("pageUrl", "/admin/course-category/" + category);
        return "admin-course-category-detail"; 
    }

    private String convertCategoryFormat(String category) {
        switch (category) {
            case "data-science":
                return "Data Science";
            case "programming":
                return "Programming";
            case "uiux-design":
                return "UI/UX Design";
            case "web-development":
                return "Web Development";
            case "artificial-intelligence":
                return "Artificial Intelligence";
            default:
                return category; 
        }
    }

    @GetMapping("/admin/course")
    public String adminCourseDetail(@RequestParam("course_id") int courseId, Model model) {
        Optional<Courses> courseDetail = courseService.getCourseById(courseId);
        List<Reviews> reviewsOfCourse = reviewService.getReviewsByCourseId(courseId);
        double averageRating = reviewService.calculateAverageRating(courseId);

        if (courseDetail.isPresent()) {
            model.addAttribute("courseDetail", courseDetail.get());
            model.addAttribute("reviewsOfCourse", reviewsOfCourse);
            model.addAttribute("averageRating", averageRating);
        } else {
            model.addAttribute("error", "Course not found");
        }

        return "admin-course-detail"; 
    }

    @GetMapping("/admin/add-course")
    public String adminAddCourse(Model model) {
        model.addAttribute("pageUrl", "/admin/add-course");
        return "admin-add-course"; 
    }

    @GetMapping("/admin/edit-course")
    public String adminEditCourse(Model model) {
        model.addAttribute("pageUrl", "/admin/edit-course");
        return "admin-edit-course"; 
    }
  
    // Show all student created accounts
    @GetMapping("/admin/student")
    public String adminStudent(Model model) {
        try {
            List<Users> users = userService.getAllStudents();

            Map<Integer, List<Enrollments>> coursesOfStudent = new HashMap<>();
            Map<Integer, Integer> totalCoursesOfEachStudent = new HashMap<>();
    
            for (Users user : users) {
                List<Enrollments> enrollments = enrollmentService.getEnrollmentsByUserId(user.getUserId());
                coursesOfStudent.put(user.getUserId(), enrollments);
                totalCoursesOfEachStudent.put(user.getUserId(), enrollments.size());
            }
    
            int totalStudents = users.size();
    
            model.addAttribute("users", users);
            model.addAttribute("coursesOfStudent", coursesOfStudent);
            model.addAttribute("totalStudents", totalStudents);
            model.addAttribute("totalCoursesOfEachStudent", totalCoursesOfEachStudent);
            model.addAttribute("pageUrl", "/admin/student");
    
            return "admin-student"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching users.");
            return "error-page";
        }
    }
    
    // Delete student
    @GetMapping("/admin/delete-student/{userId}")
    public String adminDeleteStudent(@PathVariable("userId") int userId, Model model) {
        try {
            // Delete student
            userService.deleteStudent(userId);
            
            model.addAttribute("successMessage", "Student deleted successfully.");

            return "redirect:/admin/student";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while deleting student.");
            return "error-page";
        }
    }
            

    @GetMapping("/admin/review")
    public String adminReview(Model model) {
        List<Reviews> reviews = reviewService.getAllReviews();
        List<Courses> coursesReview = courseService.getAllCourses();

        // Map to hold ratings for each course
        Map<Integer, Double> courseRatings = new HashMap<>();

        // Loop through each course and calculate average rating
        for (Courses course : coursesReview) {
            double averageRating = reviewService.calculateAverageRating(course.getCourseId());
            courseRatings.put(course.getCourseId(), averageRating);
            // Optionally, set average rating on the course object (not stored in DB)
            course.setAverageRating(averageRating); 
        }

        Map<Integer, Integer> totalStudentsPerCourse = new HashMap<>();

        for (Courses course : coursesReview) {
            List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
            totalStudentsPerCourse.put(course.getCourseId(), enrollments.size());
        }

        int highReview = 0;
        int lowReview = 0;

        for (Reviews review : reviews) {
            if(review.getRating() >= 4) {
                highReview++;
            }
            else if(review.getRating() < 4) {
                lowReview++;
            }
        }

        int totalRating = reviews.size();

        int percentHigh = (int) Math.round(((double) highReview / reviews.size()) * 100);
        int percentLow = (int) Math.round(((double) lowReview / reviews.size()) * 100);

        model.addAttribute("coursesReview", coursesReview);
        model.addAttribute("courseRatings", courseRatings);
        model.addAttribute("reviews", reviews);
        model.addAttribute("totalRating", totalRating);
        model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);
        model.addAttribute("highReview", percentHigh);
        model.addAttribute("lowReview", percentLow);
        model.addAttribute("pageUrl", "/admin/review");
        return "admin-review";
    }

    // // delete sessions funct
    // @GetMapping("/admin-delete-sessions/{sessionId}")
    // public String adminDeleteSession(@PathVariable("sessionId") int sessionId, Model model) {
    //     try {
    //         // Delete sessions
    //         adminService.deleteSession(sessionId);
            
    //         model.addAttribute("successMessage", "Session deleted successfully.");

    //         return "redirect:/admin-session";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("errorMessage", "An error occurred while deleting session.");
    //         return "error-page";
    //     }
    // }   
}
