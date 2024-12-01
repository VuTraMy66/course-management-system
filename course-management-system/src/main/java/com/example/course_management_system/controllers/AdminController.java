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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Categories;
import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Enrollments;
import com.example.course_management_system.models.Lessons;
import com.example.course_management_system.models.Reviews;
import com.example.course_management_system.models.Specializations;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.CategoryService;
import com.example.course_management_system.services.CourseService;
import com.example.course_management_system.services.EnrollmentService;
import com.example.course_management_system.services.LessonService;
import com.example.course_management_system.services.ReviewService;
import com.example.course_management_system.services.SpecializationService;
import com.example.course_management_system.services.UserService;

@Controller
public class AdminController {

    @Autowired 
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private ReviewService reviewService;
    private LessonService lessonService;
    private UserService userService;
    private CategoryService categoryService;
    private SpecializationService specializationService;
    
    public AdminController(CourseService courseService, EnrollmentService enrollmentService, ReviewService reviewService, LessonService lessonService, UserService userService, CategoryService categoryService, SpecializationService specializationService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.reviewService = reviewService;
        this.lessonService = lessonService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.specializationService = specializationService;
    }

    @RequestMapping("/admin")
    public String showAdminDashboard(Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        List<Courses> courses = courseService.getAllCourses();
        List<Users> students = userService.getAllStudents("student");
        List<Users> instructors = userService.getAllStudents("instructor");
        List<Reviews> reviews = reviewService.getAllReviews();

        Map<Integer, Integer> totalCoursesOfEachInstructor = new HashMap<>();
        Map<Integer, Integer> totalStudentsOfEachInstructor = new HashMap<>();
        Map<Integer, Integer> totalReviewsOfEachInstructor = new HashMap<>();

        for (Users instructor : instructors) {
            List<Courses> courseOfInstructor = courseService.getCourseByInstructor(instructor.getUserId());
            totalCoursesOfEachInstructor.put(instructor.getUserId(), courseOfInstructor.size());

            int totalStudentsEnroll = 0;
            int totalReviews = 0;

            for (Courses course : courseOfInstructor) {
                List<Enrollments> enrolls = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
                List<Reviews> reviewsCourse = reviewService.getReviewsByCourseId(course.getCourseId());
                totalStudentsEnroll += enrolls.size();
                totalReviews += reviewsCourse.size();
            }
            totalStudentsOfEachInstructor.put(instructor.getUserId(), totalStudentsEnroll);
            totalReviewsOfEachInstructor.put(instructor.getUserId(), totalReviews);
        }

        List<Users> sortedInstructors = instructors.stream()
            .sorted((u1, u2) -> Integer.compare(
                totalStudentsOfEachInstructor.get(u2.getUserId()),
                totalStudentsOfEachInstructor.get(u1.getUserId())
            ))
            .limit(5)
            .collect(Collectors.toList());

        Map<Integer, Integer> totalCoursesOfEachStudent = new HashMap<>();
        Map<Integer, Integer> completedCoursesOfEachStudent = new HashMap<>();

        for (Users student : students) {
            List<Enrollments> enrollments = enrollmentService.getEnrollmentsByUserId(student.getUserId());
            totalCoursesOfEachStudent.put(student.getUserId(), enrollments.size());

            int completedCoursesCount = (int) enrollments.stream()
                    .filter(enrollment -> "completed".equalsIgnoreCase(enrollment.getStatus())) 
                    .count();
            completedCoursesOfEachStudent.put(student.getUserId(), completedCoursesCount);
        }

        List<Users> sortedStudents = students.stream()
            .sorted((u3, u4) -> Integer.compare(
                totalCoursesOfEachStudent.get(u4.getUserId()),
                totalCoursesOfEachStudent.get(u3.getUserId())
                ))
            .limit(5)
            .collect(Collectors.toList());

        List<Courses> lastSixCourses = courses.stream()
            .sorted((c1, c2) -> Integer.compare(c2.getCourseId(), c1.getCourseId()))
            .limit(5)
            .collect(Collectors.toList());

        model.addAttribute("courses", lastSixCourses);
        model.addAttribute("students", sortedStudents);
        model.addAttribute("instructors", sortedInstructors);
        model.addAttribute("totalCourses", courses.size());
        model.addAttribute("totalStudents", students.size());
        model.addAttribute("totalInstructors", instructors.size());
        model.addAttribute("totalReviews", reviews.size());
        model.addAttribute("totalCoursesOfEachStudent", totalCoursesOfEachStudent);
        model.addAttribute("completedCoursesOfEachStudent", completedCoursesOfEachStudent);
        model.addAttribute("totalCoursesOfEachInstructor", totalCoursesOfEachInstructor);
        model.addAttribute("totalStudentsOfEachInstructor", totalStudentsOfEachInstructor);
        model.addAttribute("totalReviewsOfEachInstructor", totalReviewsOfEachInstructor);
        model.addAttribute("pageUrl", "/admin");
        return "admin"; 
    }

    // Show all courses
    @GetMapping("/admin/courses")
    public String adminAllCourse(Model model) {
        try {
            Users admin = AuthenticationUtils.getAuthenticatedUser(); 
            if (admin != null) {
                model.addAttribute("admin", admin);
            }

            List<Courses> coursesInAd = courseService.getAllCourses();
            
            Map<Integer, Integer> totalStudentsPerCourse = new HashMap<>();

            for (Courses course : coursesInAd) {
                List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
                totalStudentsPerCourse.put(course.getCourseId(), enrollments.size());
            }

            model.addAttribute("coursesInAd", coursesInAd);
            model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);
            model.addAttribute("pageUrl", "/admin/courses");

            return "admin-all-course";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching courses or enrollments.");
            return "error-page";
        }
    }

    @PostMapping("/admin/change-status")
    public String adminChangeStatus(Model model, @RequestParam("courseId") int courseId) {
        Optional<Courses> courseChangeStatus = courseService.getCourseById(courseId);
        if (courseChangeStatus.isPresent()) {
            Courses course = courseChangeStatus.get();
            if ("draft".equalsIgnoreCase(course.getStatus())) {
                course.setStatus("published");
            } else if ("published".equalsIgnoreCase(course.getStatus())) {
                course.setStatus("draft");
        }
            courseService.saveCourse(course);
            model.addAttribute("successMessage", "Course status changed to active.");
            return "redirect:/admin/courses";
        }
        return "redirect:/admin/courses";
    }

    @GetMapping("/admin/courses/category")
    public String adminCourseCategory(Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        List<Categories> allCategories = categoryService.getAllCategories();

        Map<String, List<Courses>> coursesByCategory = new HashMap<>();
        Map<String, Integer> totalByCategory = new HashMap<>();

        for (Categories category : allCategories) {
            List<Courses> courses = courseService.getAllCourseByCategory(category);
            coursesByCategory.put(category.getName(), courses);
            totalByCategory.put(category.getName(), courses.size());
        }

        model.addAttribute("categories", allCategories);
        model.addAttribute("coursesByCategory", coursesByCategory);
        model.addAttribute("totalByCategory", totalByCategory);
        model.addAttribute("pageUrl", "/admin/courses/category");
        return "admin-category"; 
    }

    @GetMapping("/admin/courses/category/")
    public String adminCourseCategoryDetail(@RequestParam("categoryId") int categoryId, Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        Categories categoryEntity = categoryService.getCategoryById(categoryId);

        List<Courses> coursesCategory = courseService.getAllCourseByCategory(categoryEntity);

        Map<Integer, Integer> totalStudentsPerCourse = new HashMap<>();

        for (Courses course : coursesCategory) {
            List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
            totalStudentsPerCourse.put(course.getCourseId(), enrollments.size());
        }

        int courseCount = coursesCategory.size();

        Map<Integer, Double> courseRatings = new HashMap<>();

        for (Courses courseCategory : coursesCategory) {
            double averageRating = reviewService.calculateAverageRating(courseCategory.getCourseId());
            courseRatings.put(courseCategory.getCourseId(), averageRating);

            courseCategory.setAverageRating(averageRating);
        }

        model.addAttribute("coursesCategory", coursesCategory);
        model.addAttribute("category", categoryEntity.getName());
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);
        model.addAttribute("courseRatings", courseRatings);
        model.addAttribute("pageUrl", "/admin/courses/category");
        return "admin-category-detail";
    }

    @GetMapping("/admin/course")
    public String adminCourseDetail(@RequestParam("course_id") int courseId, Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        Optional<Courses> courseDetail = courseService.getCourseById(courseId);
        List<Reviews> reviewsOfCourse = reviewService.getReviewsByCourseId(courseId);
        double averageRating = reviewService.calculateAverageRating(courseId);

        List<Enrollments> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        int totalStudentsPerCourse = enrollments.size();

        List<Lessons> lessons = lessonService.getAllLessonsOfCourse(courseId);
        int totalLessons = lessons.size();


        if (courseDetail.isPresent()) {
            model.addAttribute("courseDetail", courseDetail.get());
            model.addAttribute("reviewsOfCourse", reviewsOfCourse);
            model.addAttribute("averageRating", averageRating);
            model.addAttribute("totalStudentsPerCourse", totalStudentsPerCourse);
            model.addAttribute("lessons", lessons);
            model.addAttribute("totalLessons", totalLessons);
        } else {
            model.addAttribute("error", "Course not found");
        }

        return "admin-course-detail"; 
    }

    @GetMapping("/admin/add-course")
    public String adminAddCourse(Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        model.addAttribute("course", new Courses());
        model.addAttribute("pageUrl", "/admin/add-course");
        return "admin-add-course"; 
    }

    @GetMapping("/admin/edit-course")
    public String adminEditCourse(@RequestParam("course_id") int courseId, Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        Optional<Courses> courseEdit = courseService.getCourseById(courseId);
        if (courseEdit.isPresent()) {
            model.addAttribute("course", courseEdit.get());
        }
        model.addAttribute("pageUrl", "/admin/edit-course");
        return "admin-edit-course"; 
    }

    @PostMapping("/admin/update-course")
    public String updateCourse(@ModelAttribute("course") Courses course, int courseId) {
        Optional<Courses> existingCourse = courseService.getCourseById(courseId);
        int id = courseId;
        Courses courseToUpdate = existingCourse.get();
            courseToUpdate.setName(course.getName());
            courseToUpdate.setCategory(course.getCategory());                                                                    
            courseToUpdate.setSkillLevel(course.getSkillLevel());
            courseToUpdate.setDescription(course.getDescription());
            courseToUpdate.setDuration(course.getDuration());
            courseToUpdate.setImage(course.getImage());
            courseService.saveCourse(courseToUpdate);
        return "redirect:/admin/course?course_id=" + id;
    }

    @PostMapping("/admin/save-course")
    public String saveCourse(@ModelAttribute Courses course, @RequestParam("status") String status) {
        course.setStatus(status); // Assign the status
        courseService.saveCourse(course); // Save the course
        return "redirect:/admin/courses"; // Redirect to course list
    }

    @GetMapping("/admin/delete-course/{courseId}")
    public String deleteCourse(@PathVariable("courseId") int courseId) {
        courseService.deleteCourseById(courseId);
        return "redirect:/admin/courses";
    }

    @GetMapping("/admin/{courseId}/add-lesson")
    public String adminAddLesson(@PathVariable("courseId") int courseId, Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        model.addAttribute("lesson", new Lessons());
        model.addAttribute("pageUrl", "/admin/" + courseId + "/add-lesson");
        return "admin-add-lesson"; 
    }

    @GetMapping("/admin/edit-lesson")
    public String adminEditLesson(@RequestParam("lesson_id") int lessonId, Model model) {
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

        Optional<Lessons> lessonEdit = lessonService.getLessonById(lessonId);
        if (lessonEdit.isPresent()) {
            model.addAttribute("lesson", lessonEdit.get());
        }
        return "admin-edit-lesson"; 
    }

    @PostMapping("/admin/update-lesson")
    public String updateLesson(@ModelAttribute("lesson") Lessons lesson, int lessonId, @RequestParam("courseId") int courseId) {
        Optional<Lessons> existingLesson = lessonService.getLessonById(lessonId);

        if (existingLesson.isPresent()) {
            Lessons lessonToUpdate = existingLesson.get();
            
            lessonToUpdate.setTitle(lesson.getTitle());
            lessonToUpdate.setDescription(lesson.getDescription());
            lessonToUpdate.setVideoUrl(lesson.getVideoUrl());
            lessonToUpdate.setDuration(lesson.getDuration());
            lessonService.saveLesson(lessonToUpdate);

            // Update the total duration of the course
            Optional<Courses> courseOptional = courseService.getCourseById(courseId);
            if (courseOptional.isPresent()) {
                Courses course = courseOptional.get();  // Get the course object
                int totalDuration = lessonService.getTotalDurationByCourseId(courseId);  // Calculate total duration of lessons in the course
                course.setDuration(totalDuration);  // Set the updated duration on the course
                courseService.saveCourse(course);  // Save the updated course
            }

            return "redirect:/admin/course?course_id=" + courseId;
        } else {
            return "error";
        }
    }

    @PostMapping("/admin/save-lesson")
    public String saveLesson(@ModelAttribute Lessons lesson, @RequestParam("courseId") int courseId) {
        Optional<Courses> courseOptional = courseService.getCourseById(courseId); 
        if (courseOptional.isPresent()) {
            Courses course = courseOptional.get(); 
            lesson.setCourse(course);  
            lessonService.saveLesson(lesson);

            // Update the total duration of the course
            int totalDuration = lessonService.getTotalDurationByCourseId(courseId);
            course.setDuration(totalDuration);  // Update the course duration
            courseService.saveCourse(course);  // Save the updated course
        }
        return "redirect:/admin/course?course_id=" + courseId; // Redirect to course list
    }

    @GetMapping("/admin/delete-lesson/{lessonId}")
    public String adminDeleteLesson(@PathVariable("lessonId") int lessonId) {
        try {
            // Retrieve the lesson and its associated course in one go
            Optional<Lessons> lessonOptional = lessonService.getLessonById(lessonId);
            if (lessonOptional.isPresent()) {
                Lessons lesson = lessonOptional.get();
                Courses course = lesson.getCourse();
                int courseId = course.getCourseId();
                

                // Delete the lesson
                lessonService.deleteLesson(lessonId);

                // Recalculate the total duration of the course after lesson deletion
                int totalDuration = lessonService.getTotalDurationByCourseId(courseId);
                if (totalDuration < 0) {
                    totalDuration = 0; 
                }
                course.setDuration(totalDuration);  // Update course duration
                courseService.saveCourse(course);  // Save the updated course

                return "redirect:/admin/course?course_id=" + courseId;  // Redirect to the course page
            } else {
                return "error-page";  // Handle case when the lesson is not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error-page";  // Handle any errors that might occur during deletion
        }
    }

    @GetMapping("/admin/student")
    public String adminStudent(Model model) {
        try {
            Users admin = AuthenticationUtils.getAuthenticatedUser(); 
            if (admin != null) {
                model.addAttribute("admin", admin);
            }

            List<Users> users = userService.getAllStudents("student");

            Map<Integer, List<Enrollments>> coursesOfStudent = new HashMap<>();
            Map<Integer, Integer> totalCoursesOfEachStudent = new HashMap<>();
    
            for (Users user : users) {
                List<Enrollments> enrollments = enrollmentService.getEnrollmentsByUserId(user.getUserId());
                coursesOfStudent.put(user.getUserId(), enrollments);
                totalCoursesOfEachStudent.put(user.getUserId(), enrollments.size());
            }
    
            model.addAttribute("users", users);
            model.addAttribute("coursesOfStudent", coursesOfStudent);
            model.addAttribute("totalStudents", users.size());
            model.addAttribute("totalCoursesOfEachStudent", totalCoursesOfEachStudent);
            model.addAttribute("pageUrl", "/admin/student");
    
            return "admin-student"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching users.");
            return "error-page";
        }
    }

    @GetMapping("/admin/instructor")
    public String adminInstructor(Model model) {
        try {
            Users admin = AuthenticationUtils.getAuthenticatedUser(); 
            if (admin != null) {
                model.addAttribute("admin", admin);
            }

            List<Users> users = userService.getAllStudents("instructor");

            Map<Integer, Integer> totalCoursesOfEachInstructor = new HashMap<>();
            Map<Integer, Integer> totalStudentsOfEachInstructor = new HashMap<>();
            Map<Integer, Integer> totalReviewsOfEachInstructor = new HashMap<>();
            Map<Integer, Double> totalRatingsOfEachInstructor = new HashMap<>();
            Map<Integer, String> spec = new HashMap<>();

            for (Users user : users) {
                List<Courses> courseOfInstructor = courseService.getCourseByInstructor(user.getUserId());
                totalCoursesOfEachInstructor.put(user.getUserId(), courseOfInstructor.size());

                int totalStudentsEnroll = 0;
                int totalReviews = 0;
                int totalRatings = 0;

                for (Courses course : courseOfInstructor) {
                    List<Enrollments> enrolls = enrollmentService.getEnrollmentsByCourseId(course.getCourseId());
                    List<Reviews> reviewsCourse = reviewService.getReviewsByCourseId(course.getCourseId());

                    totalStudentsEnroll += enrolls.size();
                    totalReviews += reviewsCourse.size();

                    for (Reviews review : reviewsCourse) {
                        totalRatings += review.getRating();
                    }
                }
                totalStudentsOfEachInstructor.put(user.getUserId(), totalStudentsEnroll);
                totalReviewsOfEachInstructor.put(user.getUserId(), totalReviews);

                double averageRating = totalReviews > 0 ? (double) totalRatings / totalReviews : 0;
                totalRatingsOfEachInstructor.put(user.getUserId(), Math.round(averageRating * 10.0) / 10.0);

                Specializations special = specializationService.getSpecialization(user.getUserId());
                spec.put(user.getUserId(), special.getSpecializationName());
            }
    
    
            model.addAttribute("users", users);
            model.addAttribute("totalInstructors", users.size());
            model.addAttribute("totalCoursesOfEachInstructor", totalCoursesOfEachInstructor);
            model.addAttribute("totalStudentsOfEachInstructor", totalStudentsOfEachInstructor);
            model.addAttribute("totalRatingsOfEachInstructor", totalRatingsOfEachInstructor);
            model.addAttribute("specialization", spec);
            model.addAttribute("pageUrl", "/admin/instructor");
    
            return "admin-instructor"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching users.");
            return "error-page";
        }
    }
    
    // Delete student
    @GetMapping("/admin/delete-user/{userId}")
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
        Users admin = AuthenticationUtils.getAuthenticatedUser(); 
        if (admin != null) {
            model.addAttribute("admin", admin);
        }

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
}
