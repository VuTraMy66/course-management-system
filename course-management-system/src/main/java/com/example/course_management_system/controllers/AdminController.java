package com.example.course_management_system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Sessions;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AdminService;
import com.example.course_management_system.services.SessionService;

@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String showAdminDashboard(Model model) {
        model.addAttribute("pageUrl", "/admin");
        return "admin"; 
    }

    // Show all courses
    @GetMapping("/admin/all-course")
    public String adminAllCourse(Model model) {
        try {
            // Fetch all courses
            List<Courses> courses = adminService.getAllCourses();

            // Calculate the total number of courses
            int totalCourses = courses.size();

            // Add courses and total number of courses to the model
            model.addAttribute("courses", courses);
            model.addAttribute("totalCourses", totalCourses);

            model.addAttribute("pageUrl", "/admin/all-course");

            return "admin-all-course"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching courses.");
            return "error-page";
        }
    }

    @GetMapping("/admin/course-category")
    public String adminCourseCategory(Model model) {
        model.addAttribute("pageUrl", "/admin-course-category");
        return "admin-course-category"; 
    }

    @GetMapping("/admin/course-category2")
    public String adminCourseCategory2(Model model) {
        model.addAttribute("pageUrl", "/admin-course-category2");
        return "admin-course-category2"; 
    }

    @GetMapping("/admin/course-detail")
    public String adminCourseDetail(Model model) {
        model.addAttribute("pageUrl", "/admin-course-detail");
        return "admin-course-detail"; 
    }

    @GetMapping("/admin/add-course")
    public String adminAddCourse(Model model) {
        model.addAttribute("pageUrl", "/admin-add-course");
        return "admin-add-course"; 
    }

    @GetMapping("/admin/edit-course")
    public String adminEditCourse(Model model) {
        model.addAttribute("pageUrl", "/admin-edit-course");
        return "admin-edit-course"; 
    }
  
    @Autowired private AdminService adminService;

    // Show all student created accounts
    @GetMapping("/admin-student")
    public String adminStudent(Model model) {
        try {
            // Fetch all students
            List<Users> users = adminService.getAllStudents();

             // Calculate the total number of students
            int totalStudents = users.size();

            // Add users and total number of students to the model
            model.addAttribute("users", users);
            model.addAttribute("totalStudents", totalStudents);

            model.addAttribute("pageUrl", "/admin-student");
            
            return "admin-student"; 
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching users.");
            return "error-page";
        }
    }

    // Delete student
    @GetMapping("/admin-delete-student/{userId}")
    public String adminDeleteStudent(@PathVariable("userId") int userId, Model model) {
        try {
            // Delete student
            adminService.deleteStudent(userId);
            
            model.addAttribute("successMessage", "Student deleted successfully.");

            return "redirect:/admin-student";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while deleting student.");
            return "error-page";
        }
    }
            

    @GetMapping("/admin-review")
    public String adminReview(Model model) {
        try {
            // Fetch all reviews
            // List<Reviews> reviews = adminService.getAllStudentsAll();
    
            // Add the list of reviews to the model
            // model.addAttribute("reviews", reviews);
    
            // Calculate the total number of reviews
            // int totalReviews = reviews.size();
            // model.addAttribute("totalReviews", totalReviews);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while fetching reviews.");
        }
    
        model.addAttribute("pageUrl", "/admin-review");
        return "admin-review";
    }

    // delete sessions 
    @GetMapping("/admin-delete-sessions/{sessionId}")
    public String adminDeleteSession(@PathVariable("sessionId") int sessionId, Model model) {
        try {
            // Delete sessions
            adminService.deleteSession(sessionId);
            
            model.addAttribute("successMessage", "Session deleted successfully.");

            return "redirect:/admin-session";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while deleting session.");
            return "error-page";
        }
    }   

    @Autowired
    private SessionService sessionService;

    // Show the session creation form
    @GetMapping("/admin/create-session")
    public String showCreateSessionForm(Model model) {
        List<Courses> courses = adminService.getAllCourses();  // Get all courses
        model.addAttribute("courses", courses);  // Add courses to model
        model.addAttribute("session", new Sessions());  // Empty session object
        model.addAttribute("pageUrl", "/admin/create-session");
        return "admin-create-session"; // Show the session creation form view
    }

    // Handle the session creation form submission
    @PostMapping("/admin/create-session")
    public String createSession(@ModelAttribute Sessions session, Model model) {
        try {
            sessionService.createSession(session);  // Create session
            model.addAttribute("successMessage", "Session created successfully!");
            return "redirect:/admin";  // Redirect back to the admin dashboard
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while creating the session.");
            return "error-page"; // Error page in case of failure
        }
    }

    // Show the edit session form
    @GetMapping("/admin/edit-session/{sessionId}")
    public String showEditSessionForm(@PathVariable("sessionId") int sessionId, Model model) {
        try {
            // Retrieve the session to edit
            Sessions session = sessionService.finSessionById(sessionId).orElseThrow(() -> new Exception("Session not found"));
            List<Courses> courses = adminService.getAllCourses();  // Get all courses
 
            // Add the session and courses to the model
            model.addAttribute("session", session);
            model.addAttribute("courses", courses); 
            model.addAttribute("pageUrl", "/admin/edit-session");
            return "admin-edit-session";  // The view where the session can be edited
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An error occurred while fetching the session details.");
            return "error-page"; // Error page if session is not found
        }
    }
 
    // Handle the session edit form submission
    @PostMapping("/admin/edit-session")
    public String editSession(@ModelAttribute Sessions session, Model model) {
        try {
            // Update the session
            sessionService.updateSession(session);
            model.addAttribute("successMessage", "Session updated successfully!");
            return "redirect:/admin";  // Redirect back to the admin dashboard
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while updating the session.");
            return "error-page"; // Error page if something goes wrong
        }
    }
    
}
