package com.englishcentermanager.backend.service;

import com.englishcentermanager.backend.dto.enrollment.BulkEnrollmentRequest;
import com.englishcentermanager.backend.dto.enrollment.ClassForStudentResponse;
import com.englishcentermanager.backend.dto.enrollment.EnrollmentRequest;
import com.englishcentermanager.backend.dto.enrollment.StudentInClassResponse;
import com.englishcentermanager.backend.dto.enrollment.TransferRequest;

import java.util.List;

public interface IClassStudentService {
    void enrollStudent(EnrollmentRequest request);
    void removeStudentFromClass(Long classId, String studentId);
    List<StudentInClassResponse> getStudentsByClassId(Long classId);
    List<ClassForStudentResponse> getClassesByStudentId(String studentId);
    void transferStudent(TransferRequest request);
    void bulkEnrollStudents(BulkEnrollmentRequest request);
}