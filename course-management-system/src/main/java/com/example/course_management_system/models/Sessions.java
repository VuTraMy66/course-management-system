package com.example.course_management_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sessions")
public class Sessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionId")
    private int sessionId;

    @Column(name = "title")
    private String title;

    @Column(name = "videoUrl")
    private String videoUrl;

    @ManyToOne
        @JoinColumn(name = "courseId")
        private int courseId;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private int duration;

    // Default constructor
    public Sessions() {}

    // Constructor with parameters
    public Sessions(int sessionId, String title, String videoUrl, int courseId, String description, int duration) {
        this.sessionId = sessionId;
        this.title = title;
        this.videoUrl = videoUrl;
        this.courseId = courseId;
        this.description = description;
        this.duration = duration;
    }

    // Getter and Setter methods
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Optional: For better logging or debugging purposes
    
    /* @Override
    public String toString() {
        return "Lessons{" +
               "sessionId=" + sessionId +
               ", title='" + title + '\'' +
               ", videoUrl='" + videoUrl + '\'' +
               ", courseId=" + courseId +
               ", description='" + description + '\'' +
               ", duration=" + duration +
               '}';
    }  */
}

