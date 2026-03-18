package com.englishcentermanager.backend.service;

import com.englishcentermanager.backend.dto.course.CourseRequest;
import com.englishcentermanager.backend.dto.course.CourseResponse;
import java.util.List;

public interface ICourseService {
    CourseResponse createCourse(CourseRequest request);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
}