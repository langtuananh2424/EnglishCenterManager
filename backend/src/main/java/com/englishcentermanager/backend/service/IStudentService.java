package com.englishcentermanager.backend.service;

import com.englishcentermanager.backend.dto.student.StudentRequest;
import com.englishcentermanager.backend.dto.student.StudentResponse;

import java.util.List;

public interface IStudentService {
    StudentResponse createStudent(StudentRequest request);
    List<StudentResponse> getAllStudents();
    StudentResponse getStudentById(String id);
    StudentResponse updateStudent(String id, StudentRequest request);
    void deleteStudent(String id);
}
