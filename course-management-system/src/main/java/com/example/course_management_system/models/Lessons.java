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
@Table(name = "Lessons")
public class Lessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lessonId;

    @Column(name = "title")
    private String title;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne
        @JoinColumn(name = "course_id")
        private Courses course;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private int duration;

    public Lessons() {}

    public Lessons(int lessonId, String title, String videoUrl, Courses course, String description, int duration) {
        this.lessonId = lessonId;
        this.title = title;
        this.videoUrl = videoUrl;
        this.course = course;
        this.description = description;
        this.duration = duration;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setSessonId(int lessonId) {
        this.lessonId = lessonId;
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

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
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

    public String getFormattedDuration() {
        int hours = duration / 60;
        int minutes = duration % 60;
        return String.format("%dh %dm", hours, minutes);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

