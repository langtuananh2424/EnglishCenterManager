package com.englishcentermanager.backend.controller;

import com.englishcentermanager.backend.dto.common.ApiResponse;
import com.englishcentermanager.backend.dto.student.StudentRequest;
import com.englishcentermanager.backend.dto.student.StudentResponse;
import com.englishcentermanager.backend.service.IStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(students, "Lấy danh sách học sinh thành công"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'TEACHER')")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable String id) {
        StudentResponse student = studentService.getStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(student, "Lấy thông tin học sinh thành công"));
    }

    @PostMapping 
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse newStudent = studentService.createStudent(request);
        return ResponseEntity.ok(ApiResponse.success(newStudent, "Thêm học sinh thành công"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', STAFF)")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@PathVariable String id, @Valid @RequestBody StudentRequest request) {
        StudentResponse updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedStudent, "Cập nhật thông tin thành công"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa học sinh thành công"));
    }
}
