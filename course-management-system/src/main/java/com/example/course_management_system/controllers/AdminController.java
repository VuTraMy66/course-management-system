package com.example.course_management_system.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.course_management_system.models.Courses;
import com.example.course_management_system.models.Users;
import com.example.course_management_system.services.AccountService;
import com.example.course_management_system.services.AdminService;
import com.example.course_management_system.services.CoursesService;
import com.example.course_management_system.util.Constant;
import com.example.course_management_system.util.Encryption;

@Controller
public class AdminController {

    @Autowired 
    private AccountService accountService;
    private AdminService adminService;
    private CoursesService courseService;
    private Encryption sha_encode = new Encryption();
    private String secretKey = Constant.SECRETKEY;
	private String salt = Constant.SALT;

    

    public AdminController(AdminService adminService, CoursesService courseService) {
        this.adminService = adminService;
        this.courseService = courseService;
    }

    @RequestMapping("/admin")
    public String showAdminDashboard(Model model) {
        model.addAttribute("pageUrl", "/admin");
        return "admin"; 
    }

    // Show all courses
    @GetMapping("/admin/all-course")
    public String adminAllCourse(Model model) {
        List<Courses> coursesInAd = courseService.getAllCourses();
        model.addAttribute("coursesInAd", coursesInAd);
        return "admin-all-course";
        // try {
        //     // Fetch all courses
        //     List<Courses> courses = courseService.getAllCourses();

        //     // Calculate the total number of courses
        //     int totalCourses = courses.size();

        //     // Add courses and total number of courses to the model
        //     model.addAttribute("courses", courses);
        //     model.addAttribute("totalCourses", totalCourses);

        //     model.addAttribute("pageUrl", "/admin/all-course");

        //     return "admin-all-course"; 
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     model.addAttribute("errorMessage", "An error occurred while fetching courses.");
        //     return "error-page";
        // }
    }

    @GetMapping("/admin/course-category")
    public String adminCourseCategory(Model model) {
        model.addAttribute("pageUrl", "/admin/course-category");
        return "admin-course-category"; 
    }

    @GetMapping("/admin/course-category/{category}")
    public String adminCourseCategoryDetail(@PathVariable("category") String category, Model model) {
        String formattedCategory = convertCategoryFormat(category);
        List<Courses> coursesCategory = courseService.getAllCourseByCategory(formattedCategory);

        int courseCount = coursesCategory.size();

        model.addAttribute("coursesCategory", coursesCategory);
        model.addAttribute("category", formattedCategory);
        model.addAttribute("courseCount", courseCount);
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

    @GetMapping("/admin/course-detail")
    public String adminCourseDetail(Model model) {
        model.addAttribute("pageUrl", "/admin/course-detail");
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
            // Fetch all students
            List<Users> users = adminService.getAllStudents();

             // Calculate the total number of students
            int totalStudents = users.size();

            // Add users and total number of students to the model
            model.addAttribute("users", users);
            model.addAttribute("totalStudents", totalStudents);

            model.addAttribute("pageUrl", "/admin/student");
            
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

    // delete sessions funct
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

    @SuppressWarnings("static-access")
    @RequestMapping(value="login", method=RequestMethod.POST)
    private String login(Model model, @RequestParam("username")String username, @RequestParam("password")String password) {
        // check input
        if (username.isEmpty() || password.isEmpty()) {
			// alertMsg = "Tài khoản hoặc mật khẩu không đúng";
			// req.setAttribute("error", alertMsg);
			//req.getRequestDispatcher("/login.jsp").forward(req, resp);
			return "login";
		}
        // enscypt password

        String encode_password = sha_encode.encrypt(password, secretKey, salt);
        // 
        Users user = accountService.login(username, encode_password);
        if (user.getRole().equals("student")){
            // User info 

            //
            return "student";
        }
        else if (user.getRole().equals("admin")){
            return "admin";
        } 
        else {
            return "login";
        }

	}

    @RequestMapping(value="register", method=RequestMethod.POST)
    private String register(Model model, @RequestParam("username")String username, @RequestParam("password")String password, @RequestParam("repeatPassword")String rePassword){
		if (password.equals(rePassword)) {
            String encode_password = Encryption.encrypt(password, secretKey, salt);
            Users user = new Users(username, encode_password);
            boolean success = accountService.register(user);
            if (!success){
                return "register";
            }

            else{
                return "login";
            }
		} else {
            return "register";
		}
	}
       

}
