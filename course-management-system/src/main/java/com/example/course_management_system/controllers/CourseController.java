package com.example.course_management_system.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.models.Lessons;
import com.example.course_management_system.models.Reviews;
import com.example.course_management_system.services.CourseService;
import com.example.course_management_system.services.EnrollmentService;
import com.example.course_management_system.services.LessonService;
import com.example.course_management_system.services.ReviewService;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;
    private ReviewService reviewService;
    private EnrollmentService enrollmentService;
    private LessonService lessonService;

    public CourseController(CourseService courseService, ReviewService reviewService,EnrollmentService enrollmentService, LessonService lessonService) {
        this.courseService = courseService;
        this.reviewService = reviewService;
        this.enrollmentService = enrollmentService;
        this.lessonService = lessonService;
    }

    @GetMapping("/courses")
    public String showAllCourses(Model model) {
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
        model.addAttribute("courses", courses);
        model.addAttribute("courseRatings", courseRatings);
        model.addAttribute("courseReviewCounts", courseReviewCounts); 
        return "course";
    }

    @PostMapping("course/filter")
    public List<Courses> filterCourses(@RequestBody Map<String, Object> filters) {
        List<String> categories = (List<String>) filters.get("categories");
        double rating = Double.parseDouble(filters.get("rating").toString());
        List<String> skillLevels = (List<String>) filters.get("skillLevels");

        return courseService.filterCourses(categories, rating, skillLevels);
    }

    @GetMapping("/course")
    public String courseDetail(@RequestParam("course_id") int courseId, Model model) {
        Optional<Courses> course = courseService.getCourseById(courseId);
        List<Courses> coursesCategory = courseService.getAllCourseByCategory(course.get().getCategory());

        int excludedCourseId = course.get().getCourseId();

        List<Courses> filteredCourses = coursesCategory.stream()
            .filter(relatedCourse -> relatedCourse.getCourseId() != excludedCourseId)
            .collect(Collectors.toList());

        Collections.shuffle(filteredCourses);

        List<Courses> randomCourses = filteredCourses.stream().limit(4).collect(Collectors.toList());

        Map<Integer, Double> courseRatings = new HashMap<>();
        Map<Integer, Integer> courseReviewCounts = new HashMap<>();

        for (Courses randomCourse : randomCourses) {
            double averageRating = reviewService.calculateAverageRating(randomCourse.getCourseId());
            courseRatings.put(randomCourse.getCourseId(), averageRating);

            int reviewCount = reviewService.getReviewCount(randomCourse.getCourseId()); 
            courseReviewCounts.put(randomCourse.getCourseId(), reviewCount);

            randomCourse.setReviewCount(reviewCount);
            randomCourse.setAverageRating(averageRating); 
        }

        List<Reviews> reviewsOfCourse = reviewService.getReviewsByCourseId(courseId);
        double averageRating = reviewService.calculateAverageRating(courseId);
        int totalReviews = reviewsOfCourse.size();

        int oneStarCount = 0;
        int twoStarCount = 0;
        int threeStarCount = 0;
        int fourStarCount = 0;
        int fiveStarCount = 0;

        for (Reviews review : reviewsOfCourse) {
            int rating = review.getRating(); 
            switch (rating) {
                case 1:
                    oneStarCount++;
                    break;
                case 2:
                    twoStarCount++;
                    break;
                case 3:
                    threeStarCount++;
                    break;
                case 4:
                    fourStarCount++;
                    break;
                case 5:
                    fiveStarCount++;
                    break;
                default:
                    break;
            }
        }
    
        int oneStar = totalReviews > 0 ? (int) Math.round((double) oneStarCount / totalReviews * 100) : 0;
        int twoStar = totalReviews > 0 ? (int) Math.round((double) twoStarCount / totalReviews * 100) : 0;
        int threeStar = totalReviews > 0 ? (int) Math.round((double) threeStarCount / totalReviews * 100) : 0;
        int fourStar = totalReviews > 0 ? (int) Math.round((double) fourStarCount / totalReviews * 100) : 0;
        int fiveStar = totalReviews > 0 ? (int) Math.round((double) fiveStarCount / totalReviews * 100) : 0;

        List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        int totalStudentsPerCourse = enrollments.size();

        List<Lessons> lessons = lessonService.getAllLessonsOfCourse(courseId);
        int totalLessons = lessons.size();

        model.addAttribute("course", course.get());
        model.addAttribute("randomCourses", randomCourses);
        model.addAttribute("courseRatings", courseRatings);
        model.addAttribute("courseReviewCounts", courseReviewCounts); 
        model.addAttribute("reviewsOfCourse", reviewsOfCourse);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("totalReviews", totalReviews);
        model.addAttribute("oneStar", oneStar);
        model.addAttribute("twoStar", twoStar);
        model.addAttribute("threeStar", threeStar);
        model.addAttribute("fourStar", fourStar);
        model.addAttribute("fiveStar", fiveStar);
        model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);
        model.addAttribute("lessons", lessons);
        model.addAttribute("totalLessons", totalLessons);

        return "course-detail";
    }
}
