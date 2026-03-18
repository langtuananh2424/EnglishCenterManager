package com.englishcentermanager.backend.service.impl;

import com.englishcentermanager.backend.dto.course.CourseRequest;
import com.englishcentermanager.backend.dto.course.CourseResponse;
import com.englishcentermanager.backend.entity.Course;
import com.englishcentermanager.backend.repository.jpa.CourseRepository;
import com.englishcentermanager.backend.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new RuntimeException("Mã khóa học đã tồn tại!");
        }
        Course course = new Course();
        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setDurationInWeeks(request.getDurationInWeeks());
        course.setPrice(request.getPrice());
        course.setActive(true);

        Course saved = courseRepository.save(course);
        return mapToResponse(saved);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        return mapToResponse(course);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));

        course.setCourseName(request.getCourseName());
        course.setDescription(request.getDescription());
        course.setDurationInWeeks(request.getDurationInWeeks());
        course.setPrice(request.getPrice());

        Course updated = courseRepository.save(course);
        return mapToResponse(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khóa học để xóa");
        }
        courseRepository.deleteById(id);
    }

    private CourseResponse mapToResponse(Course c) {
        return new CourseResponse(c.getId(), c.getCourseCode(), c.getCourseName(), 
                c.getDescription(), c.getDurationInWeeks(), c.getPrice(), c.isActive());
    }
}