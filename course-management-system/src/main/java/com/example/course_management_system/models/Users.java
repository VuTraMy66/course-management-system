package com.example.course_management_system.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "avatar")
    private String avatar = "https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg";

    @Column(name = "role")
    private String role = "student";

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    // private List<Enrollments> enrollments;

    public Users() {}
    
    public Users(String username, String password, String phone, String email, String role){
        this.username = username;
        this.password = password;
        this.firstname = "";
        this.lastname = "";
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.dateCreated = LocalDateTime.now();
    }

    public Users(int userId, String username, String password, String firstname, String lastname, String phone, String email, String avatar, String role, LocalDateTime dateCreated) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.avatar = (avatar != null) ? avatar : this.avatar;
        this.role = role;
        this.dateCreated = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFormattedDateCreated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateCreated.format(formatter);
    }

    public String getRole() {
        return role;
    }

}
