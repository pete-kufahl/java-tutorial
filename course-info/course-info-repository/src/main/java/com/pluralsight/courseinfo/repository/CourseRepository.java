package com.pluralsight.courseinfo.repository;

import com.pluralsight.courseinfo.domain.Course;

import java.util.List;

public interface CourseRepository {
    void SaveCourse(Course course);

    List<Course> getAllCourses();
}
