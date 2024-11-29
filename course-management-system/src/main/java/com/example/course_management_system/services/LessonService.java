package com.example.course_management_system.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.course_management_system.models.Lessons;
import com.example.course_management_system.repositories.LessonRepository;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    public LessonService (LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lessons> getAllLessonsOfCourse(int courseId) {
        return lessonRepository.findByCourse_CourseId(courseId);
    }

    public Optional<Lessons> getLessonById(int lessonId) {
        return lessonRepository.findById(lessonId);
    }

    public void saveLesson(Lessons lesson) {
        lessonRepository.save(lesson);
    }

    public int getTotalDurationByCourseId(int courseId) {
        List<Lessons> lessons = lessonRepository.findByCourse_CourseId(courseId);
        return lessons.stream().mapToInt(Lessons::getDuration).sum();
    }

    public void deleteLesson(int lessonId) {
        lessonRepository.deleteById(lessonId);
    }
}
